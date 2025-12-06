-- BOOKS TABLE
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

-- MEMBERS TABLE
CREATE TABLE members (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL
);

-- BORROWED_BOOKS TABLE (RELATIONAL)
CREATE TABLE borrowed_books (
                                id SERIAL PRIMARY KEY,
                                member_id INT NOT NULL REFERENCES members(id) ON DELETE CASCADE,
                                book_id INT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                                borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- INDEXES
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_members_email ON members(email);
CREATE INDEX idx_borrowed_member ON borrowed_books(member_id);

-- SAMPLE DATA
INSERT INTO books (title, author, year, is_borrowed) VALUES
                                                         ('The Hobbit', 'J.R.R. Tolkien', 1937, FALSE),
                                                         ('Clean Code', 'Robert C. Martin', 2008, FALSE),
                                                         ('The Pragmatic Programmer', 'Andrew Hunt', 1999, FALSE);

INSERT INTO members (name, email) VALUES
                                      ('John Doe', 'john@example.com'),
                                      ('Alice Martin', 'alice@example.com');
