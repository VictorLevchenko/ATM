CREATE TABLE users(
  id SERIAL,
  login VARCHAR(255),
  password VARCHAR(255),
  is_admin tinyint default 0,
  UNIQUE key idx_login (login)
);

CREATE TABLE user_accounts(
  id SERIAL,
  user_id int,
  balance int,
  UNIQUE KEY idx_user_id (user_id)
);

CREATE TABLE atm_banknote_packs(
 id SERIAL,
 banknote_value int,
 banknote_num int,
 UNIQUE KEY idx_banknote_value (banknote_value)
);