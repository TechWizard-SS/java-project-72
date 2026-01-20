package hexlet.code;

import hexlet.code.dto.MainPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import java.sql.SQLException;
import java.util.Collections;
import java.net.URI; // для парсинга в POST /urls

public class App {

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(resolver, ContentType.Html);
    }

    public static Javalin getApp() throws SQLException {
        var dataSource = DataSourceConfig.getDataSource();
        UrlRepository.setDataSource(dataSource);
        UrlCheckRepository.setDataSource(dataSource);

        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.showJavalinBanner = false;
        });

        app.get("/", ctx -> {
            var page = new MainPage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flashType"));
            ctx.render("index.jte", Collections.singletonMap("page", page));
        });

        app.post("/urls", ctx -> {
            String inputUrl = ctx.formParam("url");
            try {
                var uri = new URI(inputUrl);
                var urlObj = uri.toURL();
                String normalized = urlObj.getProtocol() + "://" + urlObj.getHost()
                        + (urlObj.getPort() != -1 ? ":" + urlObj.getPort() : "");

                if (UrlRepository.findByName(normalized).isPresent()) {
                    ctx.sessionAttribute("flash", "Страница уже существует");
                    ctx.sessionAttribute("flashType", "info");
                } else {
                    Url url = new Url(normalized);
                    UrlRepository.save(url);
                    ctx.sessionAttribute("flash", "Страница успешно добавлена");
                    ctx.sessionAttribute("flashType", "success");
                }
                ctx.redirect("/urls");
            } catch (Exception e) {
                ctx.sessionAttribute("flash", "Некорректный URL");
                ctx.sessionAttribute("flashType", "danger");
                ctx.redirect("/");
            }
        });

        app.get("/urls", ctx -> {
            // Получаем номер страницы из параметров, по умолчанию 1
            int pageNumber = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            int rowsPerPage = 10;
            int offset = (pageNumber - 1) * rowsPerPage;

            var urls = UrlRepository.findAll(rowsPerPage, offset);
            var latestChecks = UrlCheckRepository.findLatestChecks();

            // Передаем в DTO текущую страницу и общее количество страниц
            int totalCount = UrlRepository.count();
            int totalPages = (int) Math.ceil((double) totalCount / rowsPerPage);

            var page = new UrlsPage(urls, latestChecks, pageNumber, totalPages);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flashType"));

            ctx.render("urls/index.jte", Collections.singletonMap("page", page));
        });

        app.get("/urls/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var url = UrlRepository.findById(id)
                    .orElseThrow(() -> new io.javalin.http.NotFoundResponse("Url not found"));
            var checks = UrlCheckRepository.findByUrlId(id);
            var page = new UrlPage(url, checks);
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            page.setFlashType(ctx.consumeSessionAttribute("flashType"));
            ctx.render("urls/show.jte", Collections.singletonMap("page", page));
        });

        app.post("/urls/{id}/checks", ctx -> {
            long urlId = ctx.pathParamAsClass("id", Long.class).get();
            var url = UrlRepository.findById(urlId)
                    .orElseThrow(() -> new io.javalin.http.NotFoundResponse("Url not found"));

            try {
                HttpResponse<String> response = Unirest.get(url.getName()).asString();
                Document doc = Jsoup.parse(response.getBody());

                int statusCode = response.getStatus();
                String title = doc.title();
                var h1El = doc.selectFirst("h1");
                String h1 = h1El != null ? h1El.text() : "";
                var descEl = doc.selectFirst("meta[name=description]");
                String description = descEl != null ? descEl.attr("content") : "";

                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                UrlCheckRepository.save(check);

                ctx.sessionAttribute("flash", "Страница успешно проверена");
                ctx.sessionAttribute("flashType", "success");
            } catch (Exception e) {
                ctx.sessionAttribute("flash", "Некорректный адрес");
                ctx.sessionAttribute("flashType", "danger");
            }
            ctx.redirect("/urls/" + urlId);
        });

        return app;
    }

    public static void main(String[] args) throws SQLException {
        var app = getApp();
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}