INSERT INTO roles(name)
VALUES ('ADMIN'), ('CONTRIBUTOR');

INSERT INTO users(username, password, firstName, lastName, enabled, avatar, created_at, role_id)
VALUES ('admin01', '$2a$10$yv3KYASIH8cFrrZgAsSkGuCOLMdPqvRCUhoz.zcCyeuPzrb5fDr9u', 'Thang', 'Le', true, 'avatar01.png', '2023-03-03 00:00:00', (SELECT id FROM roles WHERE name = 'ADMIN')),
('contributor01', '$2a$10$yv3KYASIH8cFrrZgAsSkGuCOLMdPqvRCUhoz.zcCyeuPzrb5fDr9u', 'Tu', 'Nguyen', false, 'avatar02.png', '2023-03-03 00:00:00', (SELECT id FROM roles WHERE name = 'CONTRIBUTOR'));

INSERT INTO books(title, author, description, created_at, updated_at, image, user_id)
VALUES ('Anna Karenina', 'Leo Tolstoy', 'This is the description', '2023-03-03 00:00:00', '2023-03-04 01:00:00', 'image01.png',
(SELECT id FROM users WHERE username = 'admin01' LIMIT 1)),
('To Kill a Mockingbird', 'Harper Lee', '', '2023-03-02 00:00:00', '2023-03-05 10:00:00', 'image02.png',
(SELECT id FROM users WHERE username = 'contributor01' LIMIT 1))