package br.puc.aluguelcarros.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class IndexController {

    @Get(value = "/", produces = MediaType.TEXT_HTML)
    public HttpResponse<String> index() {
        return serveResource("/public/index.html", MediaType.TEXT_HTML);
    }

    @Get(value = "/index.html", produces = MediaType.TEXT_HTML)
    public HttpResponse<String> indexHtml() {
        return serveResource("/public/index.html", MediaType.TEXT_HTML);
    }

    @Get(value = "/css/style.css", produces = "text/css")
    public HttpResponse<String> css() {
        return serveResource("/public/css/style.css", "text/css");
    }

    @Get(value = "/js/app.js", produces = "application/javascript")
    public HttpResponse<String> js() {
        return serveResource("/public/js/app.js", "application/javascript");
    }

    private HttpResponse<String> serveResource(String path, String contentType) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) return HttpResponse.notFound();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return HttpResponse.ok(content)
                    .contentType(contentType);
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}
