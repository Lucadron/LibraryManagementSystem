DROP TABLE IF EXISTS borrowed_books;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS members;

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       year INT NOT NULL CHECK (year >= 0),
                       is_borrowed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE members (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE borrowed_books (
                                id SERIAL PRIMARY KEY,
                                member_id INT NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                book_id INT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                                borrow_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO books (title, author, year, is_borrowed) VALUES
                                                         ('The Pragmatic Programmer', 'Andrew Hunt', 1999, FALSE),
                                                         ('Clean Code', 'Robert C. Martin', 2008, FALSE),
                                                         ('Effective Java', 'Joshua Bloch', 2018, FALSE),
                                                         ('Nutuk', 'Mustafa Kemal Ataturk', 1927, FALSE);


-- Örnek üyeler
INSERT INTO members (name, email) VALUES
                                      ('Emre Gülşen', 'emre@example.com'),
                                      ('Mert Adatepe', 'mert@example.com'),
                                      ('Ada Lovelace', 'ada@example.com'),
                                      ('Alan Turing', 'alan@example.com');
