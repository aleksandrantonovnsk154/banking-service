INSERT INTO transaction (id, from_account, to_account, amount, transaction_type, created)
VALUES ('75d3433f-ca12-45b7-bfc7-55ee9579002d', NULL, '233c77d0-ac7e-42b0-8a4c-97e33afdc506', 100.00, 'DEPOSIT',
        '2024-03-18 12:18:16.475091'),
       ('1ab7432c-a888-4efe-8e58-e3100ec0e2c1', '233c77d0-ac7e-42b0-8a4c-97e33afdc506',
        '0f127928-5a64-41cd-9734-1d816139513f', 50.00, 'TRANSFER', '2024-03-18 12:19:54.188379'),
       ('8dbfb0e4-e137-4729-8763-5d7401009496', '0f127928-5a64-41cd-9734-1d816139513f', NULL, 30.00, 'WITHDRAW',
        '2024-03-18 12:20:50.582395');
INSERT INTO account (account_number, beneficiary_name, pin_code, balance, created)
VALUES ('233c77d0-ac7e-42b0-8a4c-97e33afdc506', 'TestUser1',
        '$2a$10$BeBxWqR2kj8b33jlO2.OHuOi4Bzfkb9BWJ9jDNPydzqcAIw9WKjH2', 50.00, '2024-03-18 12:14:55.750158'),
       ('81de20c2-7288-44d0-9720-85236d69790d', 'TestUser1',
        '$2a$10$/zUzMXSKuA3KHW7Q/m8cSeX9kQ4MPF8Pl6Nwl6Y.wlrAo8JrW/t7O', 20.00, '2024-03-18 12:16:37.827463');