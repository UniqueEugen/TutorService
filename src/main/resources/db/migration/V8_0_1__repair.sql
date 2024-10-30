DROP TABLE comments;

-- Создание таблицы comments
CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          tutor_id BIGSERIAL,
                          user_data_id BIGSERIAL,
                          comment TEXT,
                          rating INT CHECK (rating >= 1 AND rating <= 5) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_data_id) REFERENCES users(id) ON DELETE SET NULL,
                          FOREIGN KEY (tutor_id) REFERENCES tutor(tutor_id) ON DELETE SET NULL
);