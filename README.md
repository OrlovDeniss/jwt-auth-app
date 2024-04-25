# jwt-auth-app
Реализация аутентификации и авторизации с использованием Spring Security и JWT

## Стэк
Java 17, Maven, Spring Boot 3, Spring Security, Spring Data, PostgreSQl, JUnit, Mockito

## API
URL: http://localhost:8080

- POST users/signup - запрос на регистрацию
- POST users/signin - запрос на авторизацию
- GET resources/protected - проверка доступа к защищенным данным
- GET resources/private - проверка доступа к приватным данным

Подробнее: http://localhost:8080/swagger-ui/index.html

## Сборка и запуск
1. Скопируйте репозиторий:
```Bash
git clone https://github.com/OrlovDeniss/jwt-auth-app.git
```
2. Перейдите в каталог проекта:
```Bash
cd jwt-auth-app
```
3. Скомпилируйте исходные файлы:
```Bash
mvn clean package
```
4. Запустите проект из папки target:
```Bash
java -jar jwt-auth-app-0.0.1-SNAPSHOT.jar
```