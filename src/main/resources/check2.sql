SELECT t.specialisation
FROM tutor t
WHERE t.specialisation IN (SELECT t.specialisation FROM tutor WHERE tutor.tutor_id IN (
             SELECT tutor_id FROM tutor_views WHERE user_id = 53
         ));

SELECT t.specialisation
FROM tutor t
WHERE t.tutor_id IN (
    SELECT tutor_id
    FROM tutor_views
    WHERE user_id = 53
);