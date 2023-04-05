INSERT INTO books(title, subtitle, author, publisher, isbn13, description, created_at, updated_at, image, price, year, rating, user_id)
VALUES ('Anna Karenina', 'Subtitle', 'Leo Tolstoy', 'Publisher', '1234567891234' , 'This is the description', '2023-03-03 00:00:00', '2023-03-04 01:00:00', 'image01.png', '500.0', '1998', '5.0',
(SELECT id FROM users WHERE username = 'admin01' LIMIT 1)),
('To Kill a Mockingbird', 'Subtitle 1', 'Harper Lee', 'Publisher 1', '1234567896789', 'Description', '2023-03-02 00:00:00', '2023-03-05 10:00:00', 'image02.png', '600.0', '2000', '5.0',
(SELECT id FROM users WHERE username = 'contributor01' LIMIT 1))