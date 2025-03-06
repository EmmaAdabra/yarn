INSERT INTO likes (post_Id, user_id)
VALUES(?, ?)
RETURNING id;