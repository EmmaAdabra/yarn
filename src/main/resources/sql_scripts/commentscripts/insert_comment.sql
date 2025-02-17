INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)
RETURNING id;