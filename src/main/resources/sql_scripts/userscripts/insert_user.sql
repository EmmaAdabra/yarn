INSERT INTO users (first_name, last_name, username, email, password)
VALUES (?, ?, ?, ?, ?)
RETURNING id;