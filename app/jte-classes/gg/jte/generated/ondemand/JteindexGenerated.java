package gg.jte.generated.ondemand;
import hexlet.code.dto.MainPage;
import hexlet.code.util.NamedRoutes;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,7,7,14,14,14,14,14,14,14,14,14,39,39,39,40,40,40,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, MainPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-xl mt-5\">\r\n        <div class=\"p-5 mb-4 bg-dark rounded-3 shadow\">\r\n            <div class=\"container-fluid py-5 text-white\">\r\n                <h1 class=\"display-5 fw-bold text-center\">Анализатор страниц</h1>\r\n                <p class=\"fs-4 text-center\">Бесплатно проверяйте сайты на SEO пригодность</p>\r\n\r\n                <form");
				var __jte_html_attribute_0 = NamedRoutes.urlsPath();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" method=\"post\" class=\"rss-form text-body mt-4\">\r\n                    <div class=\"row justify-content-center align-items-stretch\">\r\n                        <div class=\"col-md-8 col-lg-7\">\r\n                            <div class=\"form-floating\">\r\n                                <input type=\"text\" class=\"form-control w-100\" name=\"url\" id=\"url-input\" placeholder=\"Ссылка\" required>\r\n                                <label for=\"url-input\">Ссылка</label>\r\n                            </div>\r\n                        </div>\r\n                        <div class=\"col-md-auto mt-3 mt-md-0\">\r\n                            <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-5\">Проверить</button>\r\n                        </div>\r\n                    </div>\r\n\r\n                    <div class=\"row justify-content-center\">\r\n                        <div class=\"col-md-8 col-lg-7\">\r\n                            <p class=\"mt-2 mb-0 text-white-50\">Пример: https://www.example.com</p>\r\n                        </div>\r\n                        <div class=\"col-md-auto\">\r\n                            <div style=\"width: 175px;\" class=\"d-none d-md-block\"></div>\r\n                        </div>\r\n                    </div>\r\n                </form>\r\n            </div>\r\n        </div>\r\n    </div>\r\n");
			}
		}, page);
		jteOutput.writeContent("\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		MainPage page = (MainPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
