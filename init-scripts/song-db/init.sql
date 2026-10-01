CREATE TABLE IF NOT EXISTS songs (
    id         INTEGER PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    artist     VARCHAR(100) NOT NULL,
    album      VARCHAR(100) NOT NULL,
    duration   VARCHAR(255) NOT NULL,
    year       VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
