INSERT INTO users(username, name, password, role, description) VALUES('admin', 'Jonathan RS', '$2a$10$YrKS3b9CTdArKtwpTnMHjOTixJR5aVyQFZO6LTxKp7OgEPbTY3wyS', 'ADMINISTRATOR', 'El admin');
INSERT INTO users(username, name, password, role, description) VALUES('user', 'Juan Pablo', '$2a$10$nbie2l4TKyNFf151R8CT9OHvUK.T/jLzWlzxYOx.r27cxnf1Giwly', 'USER', 'Usuario de pruebas');
INSERT INTO users(username, name, password, role, description) VALUES('test', 'test', '$2a$10$vZKV7xePruW9BwIUz1FaDOTaGB7GzzKaa.xsecTCFxnoaqamXXqqq', 'USER', 'test');

INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'primer post del admin', 1);
INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'segundo post del admin', 1);

INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'primer post de user', 2);
INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'segundo post de user', 2);

INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'primer post de test', 3);
INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'segundo post de test', 3);