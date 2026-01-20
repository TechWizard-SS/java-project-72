@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Сайт: ${page.getUrl().getName()}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-3">

@if(page.getFlash() != null)
    <div class="alert alert-info">${page.getFlash()}</div>
@endif

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
</body>
</html>