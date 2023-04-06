# Сервис админка контрактных тестов: contract-test-admin

Сервис для просмотра результатов выполнения контрактных тестов сервисом
validator
Позволяет..

## Архитектура

Приложение состоит из бэкенда(spring-boot) и фронтенда(react)

## Локальный запуск

Запуск и отладка в docker-compose. Для локальной сборки выполнить
команду в корне проекта:
mvn clean install
Для локальной сборки образа и запуска сервиса выполнить команду:
docker-compose up -d --build.
Подключиться к админке можно по адресу http://localhost:3001

Запуск и отладка в IDEA. Для бэкенда нажать run для конфигурации
ContractAdminApplication
Для фронта в package.json изменить значение proxy на
"http://localhost:8085
из корня проекта перейти в директорию фронтед и выполнить команду:
npm run start
Для работы в docker-compose обратно вернуть значение proxy в package.json на
http://contracts-test-admin:8085
