ALTER TABLE tutor
    ADD COLUMN visit_counter BIGSERIAL;

CREATE TABLE page_visits (
                             id BIGSERIAL PRIMARY KEY,
                             tutor_id BIGSERIAL REFERENCES tutor(tutor_id) ON DELETE CASCADE,
                             visit_date DATE DEFAULT CURRENT_DATE
);