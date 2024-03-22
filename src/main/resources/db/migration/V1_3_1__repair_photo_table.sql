-- Добавляем столбец photo_oid в таблицу photos
ALTER TABLE photos
    ADD COLUMN photo_oid oid;

-- Заполняем столбец photo_oid для существующих записей
UPDATE photos
    SET photo_oid = lo_import(filename);

-- Изменяем тип столбца content на bytea
ALTER TABLE photos
    DROP COLUMN content;

-- Удаляем столбец photo_oid
ALTER TABLE photos
    RENAME COLUMN photo_oid TO content;