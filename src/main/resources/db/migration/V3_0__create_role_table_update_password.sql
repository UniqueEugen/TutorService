CREATE TABLE t_role (
                        id BIGINT PRIMARY KEY NOT NULL ,
                        name VARCHAR(255)
);

CREATE TABLE user_roles (
                            user_id BIGSERIAl,
                            role_id BIGSERIAl,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES t_role(id)
);