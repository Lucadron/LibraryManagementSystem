-- Open the Postgres query tool and run the following codes one by one

-- Create library_db
CREATE DATABASE library_db
    WITH ENCODING 'UTF8'
    TEMPLATE template0;

-- Create library_user
CREATE USER library_user WITH PASSWORD 'StrongPassword123!';

--Grant privileges to library_db
GRANT ALL PRIVILEGES ON DATABASE library_db TO library_user;
