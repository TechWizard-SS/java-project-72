package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import hexlet.code.repository.BaseRepository;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import java.io.IOException;
import java.sql.SQLException;

public class App {

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver resolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(resolver, ContentType.Html);
    }


    public static Javalin getApp() throws SQLException {
        var dataSource = DataSourceConfig.getDataSource();
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.fileRenderer(new io.javalin.rendering.template.JavalinJte(createTemplateEngine()));
            config.showJavalinBanner = false;
        });


        app.get(NamedRoutes.rootPath(), RootController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlChecksPath("{id}"), UrlController::check);

        return app;
    }

    private static void initializeDatabase() throws SQLException, IOException {
        var dataSource = DataSourceConfig.getDataSource();
        var is = App.class.getClassLoader().getResourceAsStream("schema.sql");

        if (is == null) {
            throw new RuntimeException("schema.sql not found in resources");
        }

        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
             var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {

            var sql = reader.lines().collect(java.util.stream.Collectors.joining("\n"));
            stmt.execute(sql);
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        initializeDatabase();

        var app = getApp();
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}
