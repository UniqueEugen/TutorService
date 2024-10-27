SELECT t.tutor_id
FROM tutor t
/*WHERE t.tutor_id NOT IN (
    SELECT tutor_id
    FROM tutor_views
    WHERE user_id = 53
)*/
ORDER BY
    (CASE
         WHEN t.specialisation IN (SELECT t.specialisation FROM tutor WHERE tutor.tutor_id IN (
             SELECT tutor_id FROM tutor_views WHERE user_id = 53
         )) THEN 0
         ELSE 1
        END) DESC
    --t.price ASC  -- Сначала сортируем по цене (от меньшей к большей)
    --t.rating DESC  -- Затем сортируем по рейтингу (от большего к меньшему)
    LIMIT 4;  -- Ограничиваем количество результатов



SELECT t.tutor_id
FROM tutor t
WHERE t.tutor_id NOT IN (
    SELECT tutor_id
    FROM tutor_views
    WHERE user_id = 53
)
ORDER BY
    (CASE
         WHEN t.specialisation IN (
             SELECT DISTINCT t2.specialisation
             FROM tutor t2
             WHERE t2.tutor_id IN (
                 SELECT tutor_id
                 FROM tutor_views
                 WHERE user_id = 53
             )
         ) THEN 0
         ELSE 1
        END) ASC,  -- Сначала те, у кого совпадает специализация
    t.price ASC  -- Затем по цене (от меньшей к большей)
LIMIT 3;