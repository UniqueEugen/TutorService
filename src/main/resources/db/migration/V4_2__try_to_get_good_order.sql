ALTER TABLE orders
    ADD COLUMN tutor_order_data BIGSERIAL,
    ADD COLUMN seeker_order_data BIGSERIAL;