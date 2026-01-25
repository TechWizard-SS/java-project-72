### Hexlet tests and linter status:
[![Actions Status](https://github.com/TechWizard-SS/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/TechWizard-SS/java-project-72/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=bugs)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=coverage)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=TechWizard-SS_java-project-72&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=TechWizard-SS_java-project-72)


### [Посмотреть демо на Render (если есть ссылка)]

**Анализатор страниц** — это полноценное веб-приложение на Java (Javalin), которое позволяет проверять указанные сайты на соответствие SEO-стандартам. Оно сканирует страницы, проверяет статус-код ответа и извлекает важные мета-теги (Title, H1, Description).

## 🛠 Технологии

* **Java 21**
* **Javalin** (веб-фреймворк)
* **Gradle** (система сборки)
* **JTE** (шаблонизатор для фронтенда)
* **PostgreSQL / H2** (базы данных)
* **HikariCP** (Connection Pool для БД)
* **Jsoup** (парсинг HTML)
* **Unirest** (HTTP-запросы)
* **JUnit 5 & MockWebServer** (тестирование)
* **Lombok**

## 🚀 Основные возможности

* **Валидация и нормализация URL**: Автоматическое приведение URL к единому формату (протокол + хост + порт).
* **Многопоточная проверка**: Быстрый сбор данных с внешних ресурсов.
* **SEO-анализ**: Извлечение заголовков первого уровня (H1), содержимого `<title>` и мета-описаний.
* **История проверок**: Хранение результатов всех сканирований для каждого сайта.
* **Пагинация**: Удобный просмотр списка всех добавленных сайтов.

## 💻 Установка и запуск

### Требования
* Java 21+
* PostgreSQL (опционально, по умолчанию используется H2 в памяти)

### Клонирование и сборка
```bash
git clone https://github.com/TechWizard-SS/java-project-72.git
cd app
./gradlew clean build
./gradlew run
```

## Демонстрация

<img width="1911" height="992" alt="image" src="https://github.com/user-attachments/assets/d9670af6-7b9c-40b0-9cb1-f905699a23c4" />


<img width="1910" height="950" alt="image" src="https://github.com/user-attachments/assets/02d1fd62-93a0-4c31-83fe-cd4ffb494323" />


<img width="1913" height="945" alt="image" src="https://github.com/user-attachments/assets/162bd082-a9c4-40aa-8e5a-13cd6e732f66" />


<img width="1913" height="949" alt="image" src="https://github.com/user-attachments/assets/f4eaeee1-4abc-44d1-9d82-4eba49bf31df" />
