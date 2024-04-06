ALTER TABLE seeker
    ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_seeker_user_id
    FOREIGN KEY (user_id)
    REFERENCES user_data (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;