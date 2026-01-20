@import hexlet.code.dto.MainPage
@param MainPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-xl mt-5">
        <div class="p-5 mb-4 bg-dark rounded-3 shadow">
            <div class="container-fluid py-5 text-white">
                <h1 class="display-5 fw-bold text-center">Анализатор страниц</h1>
                <p class="fs-4 text-center">Бесплатно проверяйте сайты на SEO пригодность</p>

                <form action="/urls" method="post" class="rss-form text-body mt-4">
                    <div class="row justify-content-center">
                        <div class="col-md-8 col-lg-7">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" name="url" id="url-input" placeholder="Ссылка" required>
                                <label for="url-input">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-md-auto">
                            <button type="submit" class="h-100 btn btn-lg btn-primary px-5">Проверить</button>
                        </div>
                    </div>
                </form>
                <p class="mt-2 mb-0 text-muted text-center">Пример: https://www.example.com</p>
            </div>
        </div>
    </div>
`
)