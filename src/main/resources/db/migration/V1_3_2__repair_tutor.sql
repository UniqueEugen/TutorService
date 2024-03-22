-- Изменяем столбец photo_id, разрешая значение NULL
ALTER TABLE tutor
    ALTER COLUMN photo_id DROP NOT NULL;