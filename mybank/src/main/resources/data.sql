----CREATE SEQUENCE account_sequence START WITH 1000000001 INCREMENT BY 1;

ALTER TABLE account ALTER COLUMN account_number RESTART WITH 1000000001;
INSERT INTO account (account_type, balance, overdraft, overdraft_amount) VALUES('SAVING', 2550.25, 1, 1050.36);

 