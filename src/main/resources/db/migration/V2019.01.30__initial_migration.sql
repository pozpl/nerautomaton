BEGIN;

CREATE TABLE IF NOT EXISTS users (
  `id`  INT  NOT NULL  AUTO_INCREMENT,
  username TEXT NOT NULL,
  password VARCHAR(60) NOT NULL,
  first_name TEXT,
  last_name TEXT,
  email TEXT NOT NULL,
  enabled BOOLEAN,
  is_using_2fa BOOLEAN,
  secret VARCHAR(32),
  PRIMARY KEY (`id`),
  CONSTRAINT u_username_uc UNIQUE (username(100)),
  CONSTRAINT u_email_uc UNIQUE (email(100))
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8, COLLATE utf8_general_ci
  ROW_FORMAT = COMPRESSED;

CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT r_name_uc UNIQUE (name)
)
ENGINE = InnoDB DEFAULT CHARACTER SET utf8, COLLATE utf8_general_ci ROW_FORMAT = COMPRESSED;


CREATE TABLE IF NOT EXISTS `users_roles`(
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),

  CONSTRAINT `ur_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ur_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `privileges` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT priv_name_uc UNIQUE (name)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8, COLLATE utf8_general_ci
ROW_FORMAT = COMPRESSED;

CREATE TABLE IF NOT EXISTS `roles_privileges`(
  `role_id` INT NOT NULL,
  `privilege_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `privilege_id`),

  CONSTRAINT `rp_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `rp_privilege_id_fk` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8, COLLATE utf8_general_ci ENGINE=InnoDB;


COMMIT;

