CREATE TABLE app_users (
    id           UUID PRIMARY KEY,
    email        VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    avatar_url   VARCHAR(500)
);

CREATE INDEX idx_app_users_email ON app_users (email);
