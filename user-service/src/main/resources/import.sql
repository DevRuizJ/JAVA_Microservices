INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('jruiz', '$2a$10$AUKALWJiGWzedaOWN4owJex8CZmhl9VHoSkOhn387Q4HheFIrK2lu', true, 'Javier', 'Ruiz', 'jruiz@email.com');
INSERT INTO userT (username, password, enabled, name, last_name, email) VALUES ('pgonzales', '$2a$10$drk79q1CTYl0db.MXQgbV.J3eFaToh9rNt1766/UyuzNJoCA3q2PW', true, 'Patricia', 'Gonzales', 'pgonzales@email.com');

INSERT INTO roleT (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roleT (role_name) VALUES ('ROLE_USER');

INSERT INTO user_role (userT_id, roleT_id) VALUES (1, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 1);
INSERT INTO user_role (userT_id, roleT_id) VALUES (2, 2);
