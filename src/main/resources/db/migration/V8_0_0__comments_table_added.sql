-- Создание таблицы comments
CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          tutor_id BIGSERIAL,
                          seeker_id BIGSERIAL,
                          comment TEXT,
                          rating INT CHECK (rating >= 1 AND rating <= 5) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (seeker_id) REFERENCES seeker(seeker_id) ON DELETE SET NULL,
                          FOREIGN KEY (tutor_id) REFERENCES tutor(tutor_id) ON DELETE SET NULL
);