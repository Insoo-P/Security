-- Drop the roles table first to avoid foreign key constraint issues
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS member;
DROP SEQUENCE IF EXISTS roles_seq;
-- Create the member table
CREATE TABLE member (
    member_id VARCHAR(50) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    fullName VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    PRIMARY KEY (member_id)
);

-- Create the roles table with the foreign key constraint
CREATE TABLE roles (
    roles_id NUMBER PRIMARY KEY,
    member_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    -- PRIMARY KEY (id, role),
    -- UNIQUE (id, role),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

CREATE SEQUENCE roles_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999