DROP TABLE tutor_chats;
DROP TABLE seeker_chats;
/*DROP SEQUENCE seeker_chats_seeker_seeker_id_seq;
DROP  SEQUENCE tutor_chats_tutor_tutor_id_seq;*/

DROP TABLE order_chat;

CREATE TABLE order_chat (
                            id BIGSERIAL PRIMARY KEY,
                            tutor_id BIGSERIAL,
                            seeker_id BIGSERIAL,
                            FOREIGN KEY (tutor_id) REFERENCES tutor (tutor_id) ON DELETE CASCADE,
                            FOREIGN KEY (seeker_id) REFERENCES seeker (seeker_id) ON DELETE CASCADE
);

CREATE TABLE order_message (
                               id BIGSERIAL PRIMARY KEY,
                               message TEXT,
                               date DATE,
                               time TIME,
                               type BOOLEAN,
                               chat_message_id BIGSERIAL,
                               FOREIGN KEY (chat_message_id) REFERENCES order_chat (id) ON DELETE CASCADE
);