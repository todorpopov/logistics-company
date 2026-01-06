INSERT INTO offices (office_id, address, name, phone_number)
    VALUES (1, 'Main Address', 'Head Office', 1234567890);
INSERT INTO offices (office_id, address, name, phone_number)
    VALUES (2, 'Secondary Address', 'Branch Office 1', 0987654321);
INSERT INTO offices (office_id, address, name, phone_number)
    VALUES (3, 'Tertiary Address', 'Branch Office 2', 1234512345);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (1, 'John', 'Doe', 'jdoe@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'CLIENT');
INSERT INTO clients (client_id, user_id) VALUES (1, 1);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (2, 'Max', 'Ram', 'mram@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'CLIENT');
INSERT INTO clients (client_id, user_id) VALUES (2, 2);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (3, 'Jacob', 'Jones', 'jjones@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'CLIENT');
INSERT INTO clients (client_id, user_id) VALUES (3, 3);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (4, 'Lex', 'Mans', 'lmans@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'OFFICE_EMPLOYEE');
INSERT INTO office_employees (office_employee_id, user_id, office_id) VALUES (1, 4, 1);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (5, 'Tom', 'Rogan', 'trogan@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'OFFICE_EMPLOYEE');
INSERT INTO office_employees (office_employee_id, user_id, office_id) VALUES (2, 5, 3);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (6, 'Joana', 'Max', 'jmax@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'COURIER_EMPLOYEE');
INSERT INTO courier_employees (courier_employee_id, user_id) VALUES (1, 6);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (7, 'Jay', 'Parks', 'jparks@gmail.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'COURIER_EMPLOYEE');
INSERT INTO courier_employees (courier_employee_id, user_id) VALUES (2, 7);

