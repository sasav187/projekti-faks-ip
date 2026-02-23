DROP DATABASE IF EXISTS internship_system;
CREATE DATABASE internship_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE internship_system;

-- ======================
-- USERS (renamed from user)
-- ======================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT','FACULTY','COMPANY') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- STUDENT
-- ======================

CREATE TABLE student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    index_number VARCHAR(50) NOT NULL UNIQUE,
    faculty VARCHAR(255),
    year INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ======================
-- FACULTY ADMIN
-- ======================

CREATE TABLE faculty_admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    faculty_name VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ======================
-- COMPANY
-- ======================

CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    approved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ======================
-- INTERNSHIP
-- ======================

CREATE TABLE internship (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    title VARCHAR(255),
    description TEXT,
    technologies TEXT,
    period VARCHAR(255),
    conditions TEXT,
    capacity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);

-- ======================
-- CV
-- ======================

CREATE TABLE cv (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL UNIQUE,
    summary TEXT,
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- ======================
-- SKILL (normalizovano)
-- ======================

CREATE TABLE skill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE student_skill (
    student_id BIGINT,
    skill_id BIGINT,
    level VARCHAR(50),
    PRIMARY KEY (student_id, skill_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

-- ======================
-- INTEREST
-- ======================

CREATE TABLE interest (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE student_interest (
    student_id BIGINT,
    interest_id BIGINT,
    PRIMARY KEY (student_id, interest_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES interest(id) ON DELETE CASCADE
);

-- ======================
-- EDUCATION
-- ======================

CREATE TABLE education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    institution VARCHAR(255),
    degree VARCHAR(255),
    start_year INT,
    end_year INT,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- ======================
-- WORK EXPERIENCE
-- ======================

CREATE TABLE work_experience (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    company_name VARCHAR(255),
    description TEXT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- ======================
-- INTERNSHIP APPLICATION
-- ======================

CREATE TABLE internship_application (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    status ENUM('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, internship_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- ======================
-- WORK LOG
-- ======================

CREATE TABLE work_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    week_number INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- ======================
-- EVALUATION
-- ======================

CREATE TABLE evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    evaluator_role ENUM('COMPANY','FACULTY'),
    grade INT,
    comment TEXT,
    evaluation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- ======================
-- AI RECOMMENDATION CACHE
-- ======================

CREATE TABLE recommendation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    score DOUBLE,
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(student_id, internship_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- =========================================================
-- TEST DATA
-- =========================================================

INSERT INTO users (username, password, role) VALUES
('student1','password123','STUDENT'),
('faculty1','password123','FACULTY'),
('company1','password123','COMPANY');

INSERT INTO student (user_id, index_number, faculty, year)
VALUES (1,'2023/001','Elektrotehnicki fakultet',3);

INSERT INTO faculty_admin (user_id, faculty_name)
VALUES (2,'Elektrotehnicki fakultet');

INSERT INTO company (user_id, name, description, approved)
VALUES (3,'Tech Solutions','Firma koja nudi IT prakse',TRUE);

INSERT INTO internship (company_id, title, description, technologies, period, conditions, capacity)
VALUES
(1,'Java Backend Internship','Rad na Spring Boot projektima','Java, Spring Boot, MySQL','Jun-Sep 2026','Osnove Jave',3),
(1,'Frontend Internship','Rad na Angular aplikacijama','Angular, HTML, CSS','Jul-Sep 2026','Osnove JavaScript-a',2);

INSERT INTO cv (student_id, summary)
VALUES (1,'Student sa iskustvom u Java i AI projektima.');

INSERT INTO skill (name) VALUES ('Java'),('Spring Boot'),('Angular');

INSERT INTO student_skill VALUES (1,1,'ADVANCED'),(1,2,'INTERMEDIATE');

INSERT INTO interest (name) VALUES ('Backend Development'),('Artificial Intelligence');

INSERT INTO student_interest VALUES (1,1),(1,2);

INSERT INTO education (student_id, institution, degree, start_year, end_year)
VALUES (1,'Elektrotehnicki fakultet','Bachelor of Computer Science',2023,2027);

INSERT INTO work_experience (student_id, company_name, description, start_date, end_date)
VALUES (1,'Local IT Firm','Rad na web aplikacijama','2024-06-01','2024-09-01');

INSERT INTO internship_application (student_id, internship_id)
VALUES (1,1);

INSERT INTO work_log (student_id, internship_id, week_number, description)
VALUES
(1,1,1,'Ucenje Spring Boot-a'),
(1,1,2,'Implementacija REST endpointa');

INSERT INTO evaluation (student_id, internship_id, evaluator_role, grade, comment)
VALUES (1,1,'COMPANY',5,'Odlican napredak');