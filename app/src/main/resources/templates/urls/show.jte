@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>

        <h2 class="mt-5">Проверки</h2>
        <form action="/urls/${page.getUrl().getId()}/checks" method="post">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>

        <table class="table table-bordered mt-3">
            <thead>
            <tr>
                <th>ID</th>
                <th>Код ответа</th>
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
                @for(var check : page.getChecks())
                    <tr>
                        <td>${check.getId()}</td>
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

        <a href="/urls" class="btn btn-secondary">Назад</a>
    </div>
`
)