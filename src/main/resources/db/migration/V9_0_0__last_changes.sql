ALTER TABLE tutor
    ADD COLUMN video_url varchar(255);

-- Создание таблицы для связи между пользователями и репетиторами
CREATE TABLE user_favorite_tutors (
                                      user_id BIGSERIAL NOT NULL,
                                      tutor_id BIGSERIAL NOT NULL,
                                      PRIMARY KEY (user_id, tutor_id),
                                      CONSTRAINT fk_user
                                          FOREIGN KEY (user_id)
                                              REFERENCES user_data (user_id)
                                              ON DELETE CASCADE,
                                      CONSTRAINT fk_tutor
                                          FOREIGN KEY (tutor_id)
                                              REFERENCES tutor (tutor_id)
                                              ON DELETE CASCADE
);
