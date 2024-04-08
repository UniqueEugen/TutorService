-- Удаление существующего ограничения NOT NULL
ALTER TABLE order_chat ALTER COLUMN tutor_id DROP NOT NULL;
ALTER TABLE order_chat ALTER COLUMN seeker_id DROP NOT NULL;