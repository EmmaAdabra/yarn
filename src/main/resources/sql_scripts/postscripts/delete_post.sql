DELETE FROM posts p
WHERE p.id = ? AND (p.userid = ? OR EXISTS(SELECT 1 FROM admin a WHERE a.user_id = ?))
RETURNING media;