CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles
(
    id    UUID         NOT NULL  PRIMARY KEY  DEFAULT  uuid_generate_v4(),
    name  VARCHAR(50)  NOT NULL
);

CREATE TABLE users
(
    id         UUID           NOT NULL  PRIMARY KEY  DEFAULT  uuid_generate_v4(),
    username   VARCHAR(50)    NOT NULL,
    password   VARCHAR(255)   NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    enabled    BOOLEAN        NOT NULL,
    avatar     VARCHAR(255),
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP,
    role_id    UUID           NOT NULL,
    CONSTRAINT fk_roleId
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);

CREATE TABLE books
(
    id           UUID          NOT NULL  PRIMARY KEY  DEFAULT  uuid_generate_v4(),
    title        VARCHAR(255)  NOT NULL,
    subtitle     VARCHAR(255),
    author       VARCHAR(255)  NOT NULL,
    publisher    VARCHAR(255),
    isbn13       DECIMAL,
    description  VARCHAR(255),
    created_at   TIMESTAMP     NOT NULL,
    updated_at   TIMESTAMP,
    image        VARCHAR(255),
    price        DECIMAL,
    year         INT,
    rating       DECIMAL,
    user_id	     UUID          NOT NULL,
    CONSTRAINT fk_userId
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);