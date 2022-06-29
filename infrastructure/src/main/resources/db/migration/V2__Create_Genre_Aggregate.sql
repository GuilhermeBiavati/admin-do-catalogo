CREATE TABLE genres
(
    id         VARCHAR(36)  NOT NULL PRIMARY KEY,
    name       varchar(255) NOT NULL,
    active     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
);

CREATE TABLE genres_categories
(
    genre_id    varchar(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    CONSTRAINT idx_genre_category unique (genre_id, category_id),
    CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) references genres (id) ON DELETE CASCADE,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) references category (id) ON DELETE CASCADE
);