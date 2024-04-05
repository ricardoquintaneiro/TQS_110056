CREATE TABLE books ( 
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_date DATE NOT NULL
);

INSERT INTO books (title, author, publication_date) VALUES ('The Lord of the Rings', 'J.R.R. Tolkien', '1954-07-29');
INSERT INTO books (title, author, publication_date) VALUES ('Silmarillion', 'J.R.R. Tolkien', '1977-09-15');