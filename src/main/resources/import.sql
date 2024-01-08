INSERT INTO users(username, name, password, role, description) VALUES('admin', 'Jonathan RS', '$2a$10$YrKS3b9CTdArKtwpTnMHjOTixJR5aVyQFZO6LTxKp7OgEPbTY3wyS', 'ADMINISTRATOR', 'El admin');
INSERT INTO users(username, name, password, role, description) VALUES('user', 'Juan Pablo', '$2a$10$nbie2l4TKyNFf151R8CT9OHvUK.T/jLzWlzxYOx.r27cxnf1Giwly', 'USER', 'Usuario de pruebas');
INSERT INTO users(username, name, password, role, description) VALUES('sand', 'sander', '$2a$10$PRwqdqivDwMeF.rAMBRyBukk.sxpB8Fy9GvKnFP8Bhp0Gl/IIpk4i', 'USER', 'fan del minecraft');

INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'primer post del user', 2);
INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'segundo post del user', 2);
INSERT INTO posts(created_at, content, user_id) VALUES(NOW(), 'tercer post del user', 2);