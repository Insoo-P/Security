-- Drop the roles and member tables first to avoid foreign key constraint issues
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
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- Create the sequence for the roles table
CREATE SEQUENCE roles_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999;

-- Insert sample data into the member table
INSERT INTO member (member_id, pw, fullName, email) VALUES
('admin', '$2a$10$zFuCvJDCbeQotJhWy/Bjh.P/r6gX18BZWy/UFhp3be9FTlnZoQPcm', '관리자', 'insoo1157@gmail.com');

-- Insert sample data into the roles table
INSERT INTO roles (roles_id, member_id, role) VALUES
((SELECT NEXT VALUE FOR roles_seq), 'admin', 'USER'),
((SELECT NEXT VALUE FOR roles_seq), 'admin', 'ADMIN'),
((SELECT NEXT VALUE FOR roles_seq), 'admin', 'PREMIUM');