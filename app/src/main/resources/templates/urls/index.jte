@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())
                <tr>
                    <td>${url.getId()}</td>
                    <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                    </td>
                    <td>
                        @if(page.getLatestChecks().containsKey(url.getId()))
                            ${page.getLatestChecks().get(url.getId()).getCreatedAt().toString()}
                        @endif
                    </td>
                    <td>
                        @if(page.getLatestChecks().containsKey(url.getId()))
                            ${page.getLatestChecks().get(url.getId()).getStatusCode()}
                        @endif
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item ${page.getPageNumber() <= 1 ? "disabled" : ""}">
                    <a class="page-link" href="${NamedRoutes.urlsPath()}?page=${Math.max(1, page.getPageNumber() - 1)}">Назад</a>
                </li>
                @for(int i = 1; i <= page.getTotalPages(); i++)
                    <li class="page-item ${i == page.getPageNumber() ? "active" : ""}">
                        <a class="page-link" href="${NamedRoutes.urlsPath()}?page=${i}">${i}</a>
                    </li>
                @endfor
                <li class="page-item ${page.getPageNumber() >= page.getTotalPages() ? "disabled" : ""}">
                    <a class="page-link" href="${NamedRoutes.urlsPath()}?page=${Math.min(page.getTotalPages(), page.getPageNumber() + 1)}">Вперёд</a>
                </li>
            </ul>
        </nav>
    </div>
`
)
