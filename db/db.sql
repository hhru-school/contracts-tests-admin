CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE validation_status AS ENUM ('IN_PROGRESS', 'SUCCESS', 'FAILED', 'CANCELLED');

CREATE TABLE IF NOT EXISTS verification_history (
    id uuid DEFAULT public.uuid_generate_v4() not null,
    name varchar(255) not null,
    created_date timestamp not null,
    execute_date timestamp not null,
    release_information_version varchar(255),
    validation_status varchar(30),
    error_count integer,
    PRIMARY KEY (id)
);
