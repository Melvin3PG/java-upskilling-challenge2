----CREATE SEQUENCE account_sequence START WITH 1000000001 INCREMENT BY 1;

ALTER TABLE account ALTER COLUMN account_number RESTART WITH 1000000001;
INSERT INTO account (account_type, balance, overdraft_allowed, overdraft_amount) VALUES('SAVING', 2550.25, 1, 1050.36);

 ALTER TABLE customer ALTER COLUMN customer_number RESTART WITH 10001;

INSERT INTO user_data (email, full_name, password, status, user_name) VALUES ('al@gmail.com','Aline Perez','w#CDfr546#@',1,'alineG1245');