package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import java.sql.SQLException;

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

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlChecksPath("{id}"), UrlController::check);

        return app;
    }

    public static void main(String[] args) throws SQLException {
        var app = getApp();
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
        app.start(port);
    }
}
