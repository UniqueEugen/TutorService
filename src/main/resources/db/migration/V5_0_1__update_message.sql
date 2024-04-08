-- Добавление столбца status в таблицу order_message
ALTER TABLE order_message
    ADD COLUMN status VARCHAR(255);