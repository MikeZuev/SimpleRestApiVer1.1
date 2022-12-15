CREATE TABLE IF NOT EXISTS events
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    file_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (file_id) REFERENCES files (id),
    UNIQUE (user_id, file_id)
);
