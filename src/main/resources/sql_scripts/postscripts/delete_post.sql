DELETE FROM posts
WHERE id = ? and userid = ?
returning media;