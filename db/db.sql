CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');

CREATE TABLE IF NOT EXISTS validation (
    validation_id BIGINT GENERATED ALWAYS AS IDENTITY not null,
    stand_name varchar(255) not null,
    created_date timestamptz not null,
    execute_date timestamptz,
    release_information_version varchar(255),
    validation_status validation_status,
    error_count integer,
    PRIMARY KEY (validation_id)
);
