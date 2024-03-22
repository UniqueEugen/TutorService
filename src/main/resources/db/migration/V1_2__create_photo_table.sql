CREATE TABLE photos
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    content BYTEA NOT NULL
);




-- Добавляем столбец photo_id в таблицу tutors
ALTER TABLE tutor
    ADD COLUMN photo_id BIGSERIAL;

-- Создаем внешний ключ photo_id, связанный с id в таблице photos
ALTER TABLE tutor
    ADD CONSTRAINT fk_tutor_photo
        FOREIGN KEY (photo_id)
            REFERENCES photos (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE;