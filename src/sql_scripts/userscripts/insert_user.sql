INSERT INTO users (username, email, password, first_name, last_name)
VALUES (?, ?, ?, ?, ?)
RETURNING id;