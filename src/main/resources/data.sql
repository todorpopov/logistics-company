INSERT INTO offices (office_id, address, name, phone_number)
    VALUES (1, 'Main Address', 'Head Office', '1234567890');
INSERT INTO offices (office_id, address, name, phone_number)
    VALUES (2, 'Secondary Address', 'Branch Office 1', '9876543210');

SELECT setval(pg_get_serial_sequence('offices', 'office_id'), (SELECT MAX(office_id) FROM offices));

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (1, 'client', '1', 'client1@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'CLIENT');
INSERT INTO clients (client_id, user_id) VALUES (1, 1);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (2, 'client', '2', 'client2@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'CLIENT');
INSERT INTO clients (client_id, user_id) VALUES (2, 2);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (4, 'office', 'emp1', 'officeemp1@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'OFFICE_EMPLOYEE');
INSERT INTO office_employees (office_employee_id, user_id, office_id) VALUES (1, 4, 1);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (5, 'office', 'emp2', 'officeemp2@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'OFFICE_EMPLOYEE');
INSERT INTO office_employees (office_employee_id, user_id, office_id) VALUES (2, 5, 2);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (6, 'courier', 'emp1', 'courieremp1@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'COURIER_EMPLOYEE');
INSERT INTO courier_employees (courier_employee_id, user_id) VALUES (1, 6);

INSERT INTO users (user_id, first_name, last_name, email, password_hash, user_role)
    VALUES (7, 'courier', 'emp2', 'courieremp2@logistics.com', '$2a$12$KNFsdYIHYO8D8yYV9OBzSOLJRPV3Sw1YOdJjIklL9n19Jpi6U4u/u', 'COURIER_EMPLOYEE');
INSERT INTO courier_employees (courier_employee_id, user_id) VALUES (2, 7);

SELECT setval(pg_get_serial_sequence('users', 'user_id'), (SELECT MAX(user_id) FROM users));
SELECT setval(pg_get_serial_sequence('clients', 'client_id'), (SELECT MAX(client_id) FROM clients));
SELECT setval(pg_get_serial_sequence('office_employees', 'office_employee_id'), (SELECT MAX(office_employee_id) FROM office_employees));
SELECT setval(pg_get_serial_sequence('courier_employees', 'courier_employee_id'), (SELECT MAX(courier_employee_id) FROM courier_employees));

INSERT INTO shipments (
    shipment_id,
    sender_id,
    receiver_id,
    registered_by,
    delivery_office_id,
    courier_employee_id,
    delivery_type,
    price,
    weight_gram,
    status,
    phone_number,
    sent_date,
    delivered_date
) VALUES (
    1,
    1,
    2,
    1,
    2,
    null,
    'OFFICE',
    100,
    1000,
    'REGISTERED',
    '0987654321',
    '2021-01-01',
    NULL
);

INSERT INTO shipments (
    shipment_id,
    sender_id,
    receiver_id,
    registered_by,
    delivery_office_id,
    courier_employee_id,
    delivery_type,
    price,
    weight_gram,
    status,
    phone_number,
    sent_date,
    delivered_date
) VALUES (
    2,
    1,
    2,
    2,
    null,
    2,
    'ADDRESS',
    13.45,
    1242,
    'REGISTERED',
    '1234567890',
    '2021-01-01',
    NULL
);

SELECT setval(pg_get_serial_sequence('shipments', 'shipment_id'), (SELECT MAX(shipment_id) FROM shipments));
