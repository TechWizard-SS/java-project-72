# Windows-версия (для CMD/PowerShell)
install:
	.\gradlew.bat clean install

run-dist: install
	.\build\install\app\bin\app.bat

check-updates:
	.\gradlew.bat dependencyUpdates

# Полная проверка стиля
lint:
	.\gradlew.bat checkstyleMain checkstyleTest

# Только основной код
lint-main:
	.\gradlew.bat checkstyleMain

# Проверка тестового кода
lint-test:
	.\gradlew.bat checkstyleTest

build:
	.\gradlew.bat clean build

clean:
	.\gradlew.bat clean

test:
	.\gradlew.bat test


# Unix-версия (для Git Bash/WSL)
ifeq ($(OS),Windows_NT)
    # Windows-команды выше
else


install(Unix):
	./gradlew clean install

run-dist(Unix): install
	./build/install/app/bin/app

check-updates(Unix):
	./gradlew dependencyUpdates

lint(Unix):
	./gradlew checkstyleMain

build(Unix):
	./gradlew clean build
endif

.PHONY: build