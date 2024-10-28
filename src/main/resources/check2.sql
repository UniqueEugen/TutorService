SELECT
    visit_date,
    COUNT(*) AS visit_count
FROM
    page_visits
WHERE
    tutor_id = 25
GROUP BY
    visit_date
ORDER BY
    visit_date;