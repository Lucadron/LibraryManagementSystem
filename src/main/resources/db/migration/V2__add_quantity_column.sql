ALTER TABLE books
    ADD COLUMN quantity INT NOT NULL DEFAULT 1;

UPDATE books SET quantity = 1 WHERE quantity IS NULL;
