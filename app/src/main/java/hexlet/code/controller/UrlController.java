package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collections;

public class UrlController {

    public static void index(Context ctx) throws SQLException {
        var pageNumber = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        var rowsPerPage = 10;
        var offset = (pageNumber - 1) * rowsPerPage;

        var urls = UrlRepository.findAll(rowsPerPage, offset);
        var latestChecks = UrlCheckRepository.findLatestChecks();
        var totalPages = (int) Math.ceil((double) UrlRepository.count() / rowsPerPage);

        var page = new UrlsPage(urls, latestChecks, pageNumber, totalPages);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));
        var checks = UrlCheckRepository.findByUrlId(id);

        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var inputUrl = ctx.formParam("url");
        String normalizedUrl;

        try {
            var uri = new URI(inputUrl);
            var urlObj = uri.toURL();
            normalizedUrl = urlObj.getProtocol() + "://" + urlObj.getHost()
                    + (urlObj.getPort() != -1 ? ":" + urlObj.getPort() : "");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlRepository.findByName(normalizedUrl).isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");
        } else {
            UrlRepository.save(new Url(normalizedUrl));
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashType", "success");
        }

        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(urlId)
                .orElseThrow(() -> new NotFoundResponse("Url not found"));

        try {
            var response = Unirest.get(url.getName()).asString();
            var doc = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = doc.title();
            var h1El = doc.selectFirst("h1");
            var h1 = h1El != null ? h1El.text() : "";
            var descEl = doc.selectFirst("meta[name=description]");
            var description = descEl != null ? descEl.attr("content") : "";

            var check = new UrlCheck(statusCode, title, h1, description, urlId);
            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashType", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
