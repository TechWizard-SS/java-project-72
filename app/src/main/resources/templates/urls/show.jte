@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.utils.NamedRoutes
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>

        @* Новая таблица с основной информацией о сайте *@
        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getUrl().getCreatedAt().toString()}</td>
            </tr>
            </tbody>
        </table>

        <h2 class="mt-5">Проверки</h2>
        <form action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" method="post">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>

        <table class="table table-bordered mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th class="col-1">Код ответа</th>
                <th>title</th>
                <th>h1</th>
                <th>description</th>
                <th>Дата проверки</th>
            </tr>
            </thead>
            <tbody>
            @if(page.getChecks().isEmpty())
                <tr><td colspan="6">Проверок пока не было</td></tr>
            @else
                !{var count = page.getChecks().size();}
                @for(var check : page.getChecks())
                    <tr>
                        <td>${count--}</td>
                        <td>${check.getStatusCode()}</td>
                        <td>${check.getTitle()}</td>
                        <td>${check.getH1()}</td>
                        <td>${check.getDescription()}</td>
                        <td>${check.getCreatedAt().toString()}</td>
                    </tr>
                @endfor
            @endif
            </tbody>
        </table>

        <a href="${NamedRoutes.urlsPath()}" class="btn btn-secondary">Назад</a>
    </div>
`
)