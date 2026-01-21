@import hexlet.code.dto.MainPage
@import hexlet.code.util.NamedRoutes
@param MainPage page

@template.layout.page(
page = page,
content = @`
    <div class="container-xl mt-5">
        <div class="p-5 mb-4 bg-dark rounded-3 shadow">
            <div class="container-fluid py-5 text-white">
                <h1 class="display-5 fw-bold text-center">Анализатор страниц</h1>
                <p class="fs-4 text-center">Бесплатно проверяйте сайты на SEO пригодность</p>

                <form action="${NamedRoutes.urlsPath()}" method="post" class="rss-form text-body mt-4">
                    <div class="row justify-content-center align-items-stretch">
                        <div class="col-md-8 col-lg-7">
                            <div class="form-floating">
                                <input type="text" class="form-control w-100" name="url" id="url-input" placeholder="Ссылка" required>
                                <label for="url-input">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-md-auto mt-3 mt-md-0">
                            <button type="submit" class="h-100 btn btn-lg btn-primary px-5">Проверить</button>
                        </div>
                    </div>

                    <div class="row justify-content-center">
                        <div class="col-md-8 col-lg-7">
                            <p class="mt-2 mb-0 text-white-50">Пример: https://www.example.com</p>
                        </div>
                        <div class="col-md-auto">
                            <div style="width: 175px;" class="d-none d-md-block"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
`
)
