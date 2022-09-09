CREATE TABLE notes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    person_id int,
    body      VARCHAR(255),
    FOREIGN KEY (person_id) REFERENCES persons (id)
);
