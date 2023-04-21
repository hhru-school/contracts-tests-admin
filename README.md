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

Для локальной сборки образа и запуска сервисов выполнить:
1 запустить скрипт - запуск postgres и minio: run-initial.sh
2 запустить cкрипт сборки run-build.sh 
3 запустить скрипт - сборка и запуск сервисов contracts-tests-admin и front-admin: run-services.sh 

Подключиться к админке можно по адресу http://localhost:3001

Подключиться к файловому хранилищу MinIO можно по адресу http://localhost:9001 
пользователем hhtech c паролем hhtech123.

Тестовые данные загружаются в MinIO при сборке и развертывании приложения в докере. 
Папки загружаются из minio/resources/folder-to-upload

Запуск и отладка в IDEA. Для бэкенда нажать run для конфигурации
ContractAdminApplication
Для фронта в package.json изменить значение proxy на
"http://localhost:8085
из корня проекта перейти в директорию фронтед и выполнить команду:
npm run start
Для работы в docker-compose обратно вернуть значение proxy в package.json на
http://contracts-test-admin:8085
