DELETE
FROM user;

INSERT INTO user (id, name, age, email, created_at)
VALUES (1, 'Jone', 18, 'test1@baomidou.com', NOW()),
       (2, 'Jack', 20, 'test2@baomidou.com', NOW()),
       (3, 'Tom', 28, 'test3@baomidou.com', NOW()),
       (4, 'Sandy', 21, 'test4@baomidou.com', NOW()),
       (5, 'Billie', 24, 'test5@baomidou.com', NOW());
