CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');
CREATE TYPE service_type AS ENUM ('CONSUMER', 'PRODUCER', 'CONSUMER_AND_PRODUCER', 'NOT_DEFINED');
CREATE TYPE error_level AS ENUM ('ERROR', 'WARN', 'INFO', 'FATAL');

CREATE TABLE IF NOT EXISTS validation (
    validation_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    stand_name varchar(255) not null,
    created_date timestamp not null,
    execute_date timestamp,
    release_information_version varchar(255),
    validation_status validation_status,
    error_count integer,
    PRIMARY KEY (validation_id)
);

CREATE TABLE IF NOT EXISTS service (
    service_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    created_date timestamp not null,
    service_name varchar(255) not null,
    stand_name varchar(255) not null,
    service_type service_type,
    tag varchar(30) not null,
    expectation_link varchar(255),
    shema_link varchar(255),
    PRIMARY KEY (service_id)
);

CREATE TABLE IF NOT EXISTS error (
    error_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    error_type_id BIGINT,
    comments varchar(2048),
    consumer_id BIGINT,
    producer_id BIGINT,
    http_method varchar(255),
    request_patch varchar(255),
    error_level error_level,
    expected_request varchar(4096),
    expected_response varchar(4096),
    error jsonb,
    validation_id BIGINT,
    PRIMARY KEY (error_id),
    FOREIGN KEY (validation_id) REFERENCES validation (validation_id)
);

CREATE TABLE IF NOT EXISTS error_type (
    error_type_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    error_key varchar(2048),
    comments varchar(2048),
    error_id BIGINT,
    PRIMARY KEY (error_type_id)
    );
