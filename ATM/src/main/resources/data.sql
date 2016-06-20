INSERT INTO users (login, password, is_admin) values
  ('admin','admin', 1),
  ('test','test', 0);

INSERT INTO user_accounts(user_id, balance) values
  ((SELECT id FROM users WHERE login='test'), 1000);

INSERT INTO atm_banknote_packs(banknote_value, banknote_num) VALUES
  (5, 1),
  (10, 1),
  (20, 1),
  (50, 1),
  (100, 1),
  (200, 1),
  (500, 1);