-- Drop existing constraints
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_pk;
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_username_uk;
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_email_uk;

-- Modify existing columns
ALTER TABLE users MODIFY enabled NUMBER(1) DEFAULT 1;
ALTER TABLE don_ung_tuyen MODIFY quyen_test NUMBER(1) DEFAULT 0;

-- Add new columns with default values
ALTER TABLE users ADD first_name VARCHAR2(50 CHAR) DEFAULT 'Unknown';
ALTER TABLE users ADD last_name VARCHAR2(50 CHAR) DEFAULT 'Unknown';

-- Update existing rows to have proper values
UPDATE users SET first_name = 'Unknown', last_name = 'Unknown' WHERE first_name IS NULL OR last_name IS NULL;

-- Make new columns NOT NULL after setting default values
ALTER TABLE users MODIFY first_name NOT NULL;
ALTER TABLE users MODIFY last_name NOT NULL;

-- Recreate constraints
ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT users_username_uk UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT users_email_uk UNIQUE (email); 