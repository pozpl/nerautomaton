BEGIN;

CREATE TABLE IF NOT EXISTS users (
  id           SERIAL,
  username     TEXT  NOT NULL,
  password     VARCHAR(60) NOT NULL,
  first_name   TEXT,
  last_name    TEXT,
  email        TEXT  NOT NULL,
  enabled      BOOLEAN,
  is_using_2fa BOOLEAN,
  secret       VARCHAR(32),
  PRIMARY KEY ( id),
  CONSTRAINT u_username_uc UNIQUE (username),
  CONSTRAINT u_email_uc UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS roles (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT r_name_uc UNIQUE (name)
);


CREATE TABLE IF NOT EXISTS users_roles(
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id),

  CONSTRAINT ur_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT ur_role_id_fk FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS privileges (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT priv_name_uc UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS roles_privileges(
  role_id INT NOT NULL,
  privilege_id INT NOT NULL,
  PRIMARY KEY (role_id, privilege_id),

  CONSTRAINT rp_role_id_fk FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
  CONSTRAINT rp_privilege_id_fk FOREIGN KEY (privilege_id) REFERENCES privileges (id) ON DELETE CASCADE
);


COMMIT;

