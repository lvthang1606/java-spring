CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE books
(
    id   UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL
);