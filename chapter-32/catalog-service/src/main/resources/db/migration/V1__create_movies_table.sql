CREATE TABLE movies (
    id          UUID PRIMARY KEY,
    tmdb_id     BIGINT UNIQUE,
    title       VARCHAR(255) NOT NULL,
    release_year INT,
    overview    TEXT,
    poster_path VARCHAR(500)
);

CREATE INDEX idx_movies_title ON movies (LOWER(title));
