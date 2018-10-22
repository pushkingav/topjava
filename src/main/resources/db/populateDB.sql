DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (meal_id, user_id, description, calories, date_time) VALUES
(100003, 100000, 'Завтрак', 500, '2015-05-30 10:00:00.879468'),
(100004, 100000, 'Обед', 1000, '2015-05-30 13:00:00.438532'),
(100005, 100000, 'Ужин', 500, '2015-05-30 20:00:00.332587'),
(100006, 100000, 'Завтрак', 500, '2015-05-31 10:07:30.779777'),
(100007, 100000, 'Обед', 1000, '2015-05-31 12:45:15.438532'),
(100008, 100000, 'Ужин', 501, '2015-05-31 20:01:01.332587'),
(100009, 100001, 'Завтрак', 500, '2015-05-30 10:05:00.879468'),
(100010, 100001, 'Обед', 1000, '2015-05-30 13:05:00.438532'),
(100011, 100001, 'Ужин', 501, '2015-05-30 20:05:00.332587');