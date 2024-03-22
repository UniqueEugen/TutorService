ALTER TABLE user_data
    ALTER COLUMN seeker_id DROP NOT NULL,
    ALTER COLUMN tutor_id DROP NOT NULL;

ALTER TABLE forum_message
    ALTER COLUMN user_data_id DROP NOT NULL;

ALTER TABLE orders
    ALTER COLUMN seeker_order DROP NOT NULL,
    ALTER COLUMN tutor_order DROP NOT NULL;
