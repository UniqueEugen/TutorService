CREATE TABLE tutor_views (
                             user_id BIGINT NOT NULL,
                             tutor_id BIGINT NOT NULL,
                             viewed_at TIMESTAMP DEFAULT NOW(),
                             PRIMARY KEY (user_id, tutor_id),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (tutor_id) REFERENCES tutor(tutor_id) ON DELETE CASCADE
);