-- Удаляем существующие ограничения на внешние ключи
-- (если они были созданы ранее без опции ON DELETE CASCADE)
ALTER TABLE user_data DROP CONSTRAINT IF EXISTS fkboeinboxcrb4ilnj0sfyxsbol;
ALTER TABLE user_data DROP CONSTRAINT IF EXISTS fkifhlhujafb301og86785umy8l;
ALTER TABLE user_data DROP CONSTRAINT IF EXISTS fk1wuclc2b5ryaty4vihpgs4w9b;

-- Добавляем ограничения на внешние ключи с опцией ON DELETE CASCADE
ALTER TABLE user_data
    ADD CONSTRAINT fkboeinboxcrb4ilnj0sfyxsbol
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;

ALTER TABLE user_data
    ADD CONSTRAINT fkifhlhujafb301og86785umy8l
        FOREIGN KEY (seeker_id)
            REFERENCES seeker (seeker_id)
            ON DELETE CASCADE;

ALTER TABLE user_data
    ADD CONSTRAINT fk1wuclc2b5ryaty4vihpgs4w9b
        FOREIGN KEY (tutor_id)
            REFERENCES tutor (tutor_id)
            ON DELETE CASCADE;

-- Удаляем существующие ограничения на внешние ключи
-- (если они были созданы ранее без опции ON DELETE CASCADE)
ALTER TABLE tutor DROP CONSTRAINT IF EXISTS fk9wuvkfmgrk9xqkcja77abb1ma;

-- Добавляем ограничение на внешний ключ address_id с опцией ON DELETE CASCADE
ALTER TABLE tutor
    ADD CONSTRAINT fk9wuvkfmgrk9xqkcja77abb1ma
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE CASCADE;