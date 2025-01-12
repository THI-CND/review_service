CREATE TABLE review_entity (
    id        BIGSERIAL PRIMARY KEY,
    recipe_id VARCHAR(255) NOT NULL,
    author    VARCHAR(255) NOT NULL,
    rating    FLOAT        NOT NULL,
    comment   TEXT
);