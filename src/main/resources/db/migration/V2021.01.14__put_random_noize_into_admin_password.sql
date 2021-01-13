BEGIN;
UPDATE users
SET
    password = '$2y$12$5kfmxk5JxN.P0XnLU0BPyeo56geJaoXDKzkYGo5lMZ8VJoB1mldDy'
WHERE username = 'admin';
COMMIT;