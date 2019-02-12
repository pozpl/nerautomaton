BEGIN;

INSERT IGNORE INTO users(username, password, first_name, last_name, email, enabled, is_using_2fa, secret ) VALUES
("admin", "$2y$11$LWtlxi2Z6d7W3TJOf5GPNeptEbVyojtPxz.bliT2Pd4jXhH7Yg.Hi",
"Admin", "Admin", "Admin Email", true, false, NULL );

INSERT IGNORE INTO roles(`name`) VALUES
("ROLE_USER"),
("ROLE_ADMIN"),
("ROLE_PRIVILEGED_USER");

INSERT IGNORE INTO privileges(`name`) VALUES
("READ_PRIVILEGE"),
("WRITE_PRIVILEGE");

INSERT IGNORE INTO users_roles(user_id, role_id)
SELECT u.id, r.id FROM users AS u
JOIN roles AS r ON r.name = "ROLE_ADMIN"
WHERE username = "admin";

COMMIT;