# Windows-версия (для CMD/PowerShell)
install:
	.\gradlew.bat clean install

run-dist: install
	.\build\install\app\bin\app.bat

check-updates:
	.\gradlew.bat dependencyUpdates

lint:
	.\gradlew.bat checkstyleMain

build:
	.\gradlew.bat clean build

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

.PHONY: build install run-dist check-updates lint