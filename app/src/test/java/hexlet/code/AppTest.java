package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;


public final class AppTest {

    private static MockWebServer mockServer;

    @BeforeAll
    public static void beforeAll() throws IOException, SQLException {
        mockServer = new MockWebServer();
        mockServer.start();

        var dataSource = DataSourceConfig.getDataSource();
        // Читаем скрипт ОДИН раз для создания таблиц
        try (var is = App.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (is == null) {
                throw new RuntimeException("File schema.sql not found");
            }
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                 var conn = dataSource.getConnection();
                 var stmt = conn.createStatement()) {
                var sql = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
                stmt.execute(sql);
            }
        }
    }

    @AfterAll
    public static void stopMockServer() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // Очистка базы перед каждым тестом для изоляции
        try (var conn = DataSourceConfig.getDataSource().getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM url_checks");
            stmt.execute("DELETE FROM urls");
        }
    }

    @Test
    public void testMainPage() throws SQLException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testCreateUrl() throws SQLException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            var requestBody = "url=https://hexlet.io";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://hexlet.io");

            // Проверка, что сущность добавлена в БД
            assertThat(UrlRepository.findByName("https://hexlet.io")).isPresent();
        });
    }

    @Test
    public void testUrlsPage() throws SQLException {
        Url url = new Url("https://ya.ru");
        UrlRepository.save(url);

        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://ya.ru");
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        Url url = new Url("https://google.com");
        UrlRepository.save(url);

        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://google.com");
        });
    }

    @Test
    public void testCheckUrl() throws IOException, SQLException {
        // Подготовка Mock сервера
        String mockUrl = mockServer.url("/").toString();
        String htmlBody = "<html>"
                + "<head><title>Test Title</title><meta name=\"description\" content=\"Test Description\"></head>"
                + "<body><h1>Test H1</h1></body>"
                + "</html>";

        mockServer.enqueue(new MockResponse().setBody(htmlBody).setResponseCode(200));

        JavalinTest.test(App.getApp(), (server, client) -> {
            // Создаем URL
            String normalizedUrl = mockUrl.endsWith("/") ? mockUrl.substring(0, mockUrl.length() - 1) : mockUrl;
            Url url = new Url(normalizedUrl);
            UrlRepository.save(url);

            // Запускаем проверку
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);

            // Проверяем данные в БД и на странице
            var checks = UrlCheckRepository.findByUrlId(url.getId());
            assertThat(checks).isNotEmpty();

            var lastCheck = checks.get(0);
            assertThat(lastCheck.getStatusCode()).isEqualTo(200);
            assertThat(lastCheck.getTitle()).isEqualTo("Test Title");
            assertThat(lastCheck.getH1()).isEqualTo("Test H1");
            assertThat(lastCheck.getDescription()).isEqualTo("Test Description");

            // Проверяем отображение на странице
            var body = response.body().string();
            assertThat(body).contains("Test Title");
            assertThat(body).contains("Test H1");
            assertThat(body).contains("Test Description");
        });
    }

    @Test
    public void testCreateInvalidUrl() throws SQLException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            var requestBody = "url=invalid-url";
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200); // Мы редиректим обратно на главную
            // Проверяем, что в базе ничего не появилось
            assertThat(UrlRepository.findByName("invalid-url")).isEmpty();
        });
    }

    @Test
    public void testCreateDuplicateUrl() throws SQLException {
        var url = "https://hexlet.io";
        UrlRepository.save(new Url(url));

        JavalinTest.test(App.getApp(), (server, client) -> {
            var requestBody = "url=" + url;
            var response = client.post("/urls", requestBody);

            assertThat(response.code()).isEqualTo(200);
            // Проверяем, что в базе по-прежнему только одна запись
            assertThat(UrlRepository.count()).isEqualTo(1);
        });
    }

    @Test
    public void testUrlNotFound() throws SQLException {
        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.get("/urls/9999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheckUrlError() throws SQLException {
        // Создаем реальный URL, но MockServer вернет ошибку 500
        mockServer.enqueue(new MockResponse().setResponseCode(500));

        Url url = new Url("http://localhost:" + mockServer.getPort());
        UrlRepository.save(url);

        JavalinTest.test(App.getApp(), (server, client) -> {
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);

            // Проверяем, что проверка сохранилась, даже если статус был 500
            var checks = UrlCheckRepository.findByUrlId(url.getId());
            assertThat(checks).isNotEmpty();
            assertThat(checks.get(0).getStatusCode()).isEqualTo(500);
        });
    }
}
