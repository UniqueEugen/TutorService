DROP TABLE tutor_views;

CREATE TABLE tutor_views (
                             id SERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             tutor_id BIGINT NOT NULL,
                             viewed_at TIMESTAMP DEFAULT NOW(),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (tutor_id) REFERENCES tutor(tutor_id) ON DELETE CASCADE
);