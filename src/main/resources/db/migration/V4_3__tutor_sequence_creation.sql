ALTER TABLE tutor
    ADD COLUMN user_id INTEGER,
ADD CONSTRAINT fk_tutor_user_id
    FOREIGN KEY (user_id)
    REFERENCES user_data (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;