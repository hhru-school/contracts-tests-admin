CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');
CREATE TYPE service_type AS ENUM ('CONSUMER', 'PRODUCER', 'CONSUMER_AND_PRODUCER', 'NOT_DEFINED');
CREATE TYPE error_level AS ENUM ('ERROR', 'WARN', 'INFO', 'FATAL');
CREATE TYPE http_method AS ENUM ('GET', 'POST', 'DELETE', 'PUT', 'HEAD', 'PATCH', 'OPTIONS');

CREATE TABLE IF NOT EXISTS validation
(
    validation_id               BIGINT GENERATED ALWAYS AS IDENTITY not null,
    stand_name                  varchar(255)                        not null,
    creation_date               timestamptz                         not null,
    execution_date              timestamptz,
    release_information_version varchar(255),
    report                      jsonb,
    validation_status           validation_status,
    error_count                 integer,
    PRIMARY KEY (validation_id)
);

CREATE TABLE IF NOT EXISTS service
(
    service_id       BIGINT GENERATED ALWAYS AS IDENTITY not null,
    creation_date    timestamptz                         not null,
    service_name     varchar(255)                        not null,
    stand_name       varchar(255)                        not null,
    service_type     service_type,
    tag              varchar(30)                         not null,
    expectation_link varchar(255),
    schema_link      varchar(255),
    PRIMARY KEY (service_id)
);

CREATE TABLE IF NOT EXISTS error_type
(
    error_type_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    error_key     varchar(2048) UNIQUE,
    comments      varchar(4096),
    PRIMARY KEY (error_type_id)
);

CREATE TABLE IF NOT EXISTS expectation
(
    expectation_id   BIGINT GENERATED ALWAYS AS IDENTITY not null,
    http_method      http_method,
    consumer_id      BIGINT,
    producer_id      BIGINT,
    request_path     varchar(2048),
    request_headers  varchar(2048),
    query_params     varchar(2048),
    request_body     varchar(4096),
    response_status  smallint,
    response_headers varchar(2048),
    response_body    varchar(4096),
    validation_id    BIGINT,
    PRIMARY KEY (expectation_id),
    FOREIGN KEY (validation_id) REFERENCES validation (validation_id),
    FOREIGN KEY (consumer_id) REFERENCES service (service_id),
    FOREIGN KEY (producer_id) REFERENCES service (service_id)
);

CREATE TABLE IF NOT EXISTS error
(
    error_id       BIGINT GENERATED ALWAYS AS IDENTITY not null,
    error_type_id  BIGINT,
    comments       varchar(2048),
    expectation_id BIGINT,
    error_level    error_level,
    PRIMARY KEY (error_id),
    FOREIGN KEY (expectation_id) REFERENCES expectation (expectation_id),
    FOREIGN KEY (error_type_id) REFERENCES error_type (error_type_id)
);
