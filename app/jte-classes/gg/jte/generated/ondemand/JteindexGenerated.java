package gg.jte.generated.ondemand;
import hexlet.code.dto.MainPage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,6,6,30,30,30,30,30,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, MainPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-xl mt-5\">\r\n        <div class=\"p-5 mb-4 bg-dark rounded-3 shadow\">\r\n            <div class=\"container-fluid py-5 text-white\">\r\n                <h1 class=\"display-5 fw-bold text-center\">Анализатор страниц</h1>\r\n                <p class=\"fs-4 text-center\">Бесплатно проверяйте сайты на SEO пригодность</p>\r\n\r\n                <form action=\"/urls\" method=\"post\" class=\"rss-form text-body mt-4\">\r\n                    <div class=\"row justify-content-center\">\r\n                        <div class=\"col-md-8 col-lg-7\">\r\n                            <div class=\"form-floating mb-3\">\r\n                                <input type=\"text\" class=\"form-control\" name=\"url\" id=\"url-input\" placeholder=\"Ссылка\" required>\r\n                                <label for=\"url-input\">Ссылка</label>\r\n                            </div>\r\n                        </div>\r\n                        <div class=\"col-md-auto\">\r\n                            <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-5\">Проверить</button>\r\n                        </div>\r\n                    </div>\r\n                </form>\r\n                <p class=\"mt-2 mb-0 text-muted text-center\">Пример: https://www.example.com</p>\r\n            </div>\r\n        </div>\r\n    </div>\r\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		MainPage page = (MainPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
