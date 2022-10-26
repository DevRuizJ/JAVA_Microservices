INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('jruiz', '12345', 1, 'Javier', 'Ruiz', 'jruiz@email.com');
INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('pgonzales', '12345', 1, 'Patricia', 'Gonzales', 'pgonzales@email.com');

INSERT INTO roleT (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roleT (role_name) VALUES ('ROLE_USER');

INSERT INTO user_role (userT_id, roleT_id) VALUES (1, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 2);
