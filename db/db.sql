CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');

CREATE TABLE IF NOT EXISTS verification_history (
    verification_history_id uuid BIGINT GENERATED ALWAYS AS IDENTITY not null,
    name varchar(255) not null,
    created_date timestamp not null,
    execute_date timestamp not null,
    release_information_version varchar(255),
    validation_status varchar(30),
    error_count integer,
    PRIMARY KEY (id)
);
