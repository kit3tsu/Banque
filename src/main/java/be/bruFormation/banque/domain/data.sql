INSERT INTO Bank (name,swift_code) VALUES ('PicsouBanque','BBRUBEBCXXX');
INSERT INTO Holder(national_number, last_name, first_name) VALUES ('19910719-443', 'Ovyn', 'Flavian');
INSERT INTO Account(number, credit_line, last_withdrawn_date, desc, holder_id, bank_id)
VALUES ('BE12 1234 1234 1234', 50, NULL, 'CURRENT', 1, 1),('BE12 1234 1234 1235', null, '2022-06-02', 'SAVING', 1,1);