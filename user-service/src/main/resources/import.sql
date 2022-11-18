INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('jruiz', '$2a$10$Q4eplUvrS/sT27zSQi4Ti.wkDvAvFjiQtEMzjEA9TPdfWxs0zHkPa', 1, 'Javier', 'Ruiz', 'jruiz@email.com');
INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('pgonzales', '$2a$10$Q4eplUvrS/sT27zSQi4Ti.wkDvAvFjiQtEMzjEA9TPdfWxs0zHkPa', 1, 'Patricia', 'Gonzales', 'pgonzales@email.com');

INSERT INTO roleT (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roleT (role_name) VALUES ('ROLE_USER');

INSERT INTO user_role (userT_id, roleT_id) VALUES (1, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 2);
