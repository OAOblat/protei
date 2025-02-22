# Запуск тестов и генерация отчета Allure

## 1. Установка зависимостей и инструментов для теста
Перед запуском тестов убедитесь, что установлены все необходимые зависимости:

```
./gradlew dependencies
```

Если allure не установлен, установите его:

- Для macOS (через Homebrew):

```
brew install allure
```

- Для Windows (через Scoop):

```
scoop install allure
```
### Установка Google Chrome и ChromeDriver
Тесты выполняются в браузере Google Chrome, поэтому он должен быть установлен.

ChromeDriver необходим для работы Selenium.
- Для macOS (через Homebrew):
```agsl
brew install chromedriver
```

- Для Windows (через Scoop):
```agsl
scoop install chromedriver
```

Проверьте, что ChromeDriver установлен и доступен в системе:
```agsl
chromedriver --version
```

## 2. Запуск тестов

Для запуска тестов выполните команду (тесты по умолчанию запускаются в headless-режиме):

```
./gradlew clean test
```

После выполнения тестов результаты будут сохранены в папке build/allure-results.

## 3. Как отобразить Allure-отчет из папки

Чтобы отобразить Allure-отчет из папки `allure-results`, выполните следующую команду в терминале:

```
allure serve build/allure-results
```

Что делает эта команда:

- Собирает отчет из allure-results
- Запускает локальный сервер
- Автоматически открывает отчет в браузере

### Проверка установки Allure:

```
allure --version
```

Если команда не найдена, установите Allure:

Для macOS (через Homebrew):
```
brew install allure
```

Для Windows (через Scoop):
```
scoop install allure
```
После установки снова выполните команду allure serve.
