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

Для локальной сборки образа и запуска сервисов выполнить команду:
docker-compose up -d --build.

Подключиться к админке можно по адресу http://localhost:3001
Подключиться к файловому хранилищу MinIO можно по адресу http://localhost:9001 
пользователем hhtech c паролем hhtech123.

Для загрузки тестовых данные в файловое хранилище поле логина в Web интерфейс MinIO надо перейти в раздел Backets и нажать на Create Buckets. 
Создать bucket с именем "contract-release". Далее перейти в Object Browser, зайти в backet contract-release и нажать кнопку upload, 
далее выбрать Upload Folder, и загрузить папку expectiations из minio/folder-to-upload/contract-realese. 
Так же загрузить папку schema из minio/folder-to-upload/contract-realese.
Создать еще одну bucket ts107.pyn.ru-contracts и загрузить в нее expectations и schema из minio/folder-to-upload/ts107.pyn.ru-contracts
На данный момент 2 папки содержат одни и те же файлы. 

Запуск и отладка в IDEA. Для бэкенда нажать run для конфигурации
ContractAdminApplication
Для фронта в package.json изменить значение proxy на
"http://localhost:8085
из корня проекта перейти в директорию фронтед и выполнить команду:
npm run start
Для работы в docker-compose обратно вернуть значение proxy в package.json на
http://contracts-test-admin:8085
