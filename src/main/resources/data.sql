INSERT INTO users (id, email, firstname, lastname, password_hash, phone_number, birth_date)
VALUES
    ('1', 'test1@mail.com', 'test1', 'test1', '$2a$10$HvE3TTHqLhz84OmKwXjRZu7J3cvYl/PdkPNnz49f0cMLxxzWgnX9C', '123456789', '1990-01-01'),
    ('2', 'test2@mail.com', 'test2', 'test2', '$2a$10$HvE3TTHqLhz84OmKwXjRZu7J3cvYl/PdkPNnz49f0cMLxxzWgnX9C', '234567890', '1985-05-15'),
    ('3', 'test3@mail.com', 'test3', 'test3', '$2a$10$HvE3TTHqLhz84OmKwXjRZu7J3cvYl/PdkPNnz49f0cMLxxzWgnX9C', '345678901', '1995-07-20'),
    ('4', 'test4@mail.com', 'test4', 'test4', '$2a$10$HvE3TTHqLhz84OmKwXjRZu7J3cvYl/PdkPNnz49f0cMLxxzWgnX9C', '456789012', '1988-11-30'),
    ('5', 'test5@mail.com', 'test5', 'test5', '$2a$10$HvE3TTHqLhz84OmKwXjRZu7J3cvYl/PdkPNnz49f0cMLxxzWgnX9C', '567890123', '1992-03-10');

INSERT INTO organizations (id, name, date_of_creation)
VALUES
    /*('1', 'Organization 1', '2022-01-01T00:00:00Z', 'https://example.com/banner1.jpg'),
    ('2', 'Organization 2', '2022-02-15T12:30:00Z', 'https://example.com/banner2.jpg'),
    ('3', 'Organization 3', '2022-03-10T08:45:00Z', 'https://example.com/banner3.jpg'),
    ('4', 'Organization 4', '2022-04-20T16:20:00Z', 'https://example.com/banner4.jpg'),
    ('5', 'Organization 5', '2022-05-05T09:00:00Z', 'https://example.com/banner5.jpg');*/
    ('1', 'Organization 1', '2022-01-01T00:00:00Z'),
    ('2', 'Organization 2', '2022-02-15T12:30:00Z'),
    ('3', 'Organization 3', '2022-03-10T08:45:00Z'),
    ('4', 'Organization 4', '2022-04-20T16:20:00Z'),
    ('5', 'Organization 5', '2022-05-05T09:00:00Z');

INSERT INTO members (id, name, is_still_member, organization_id, user_id)
VALUES
    ('1', 'Owner', TRUE, 1, 1),
    ('2', 'Owner', TRUE, 2, 2),
    ('3', 'Owner', TRUE, 3, 3),
    ('4', 'Owner', TRUE, 4, 4),
    ('5', 'Owner', TRUE, 5, 5);