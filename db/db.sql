CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');
CREATE TYPE service_type AS ENUM ('CONSUMER', 'PRODUCER', 'CONSUMER_AND_PRODUCER', 'NOT_DEFINED');

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

CREATE TABLE IF NOT EXISTS validation_info (
    created_date timestamp not null,
    validation_id BIGINT,
    consumer_id BIGINT,
    producer_id BIGINT,
    PRIMARY KEY (validation_id),
    FOREIGN KEY (validation_id) REFERENCES validation (validation_id),
    FOREIGN KEY (consumer_id) REFERENCES service (service_id),
    FOREIGN KEY (producer_id) REFERENCES service (service_id)
);

CREATE TABLE IF NOT EXISTS error_info (
    error_info_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    error_key varchar(2048),
    comments varchar(2048),
    error jsonb,
    validation_id BIGINT,
    PRIMARY KEY (error_info_id),
    FOREIGN KEY (validation_id) REFERENCES validation_info (validation_info_id)
);
