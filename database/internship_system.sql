DROP DATABASE IF EXISTS internship_system;
CREATE DATABASE internship_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE internship_system;

-- ======================
-- USER TABLE
-- ======================

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT','FACULTY','COMPANY') NOT NULL
);

-- ======================
-- STUDENT
-- ======================

CREATE TABLE student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    index_number VARCHAR(50) NOT NULL UNIQUE,
    faculty VARCHAR(255),
    year INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- ======================
-- COMPANY
-- ======================

CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);

-- ======================
-- CV
-- ======================

CREATE TABLE cv (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    summary TEXT,
    image_path VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- ======================
-- SKILL
-- ======================

CREATE TABLE skill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    name VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- ======================
-- INTEREST
-- ======================

CREATE TABLE interest (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    name VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
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
    grade INT,
    comment TEXT,
    evaluation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- ======================
-- OPTIONAL: RECOMMENDATION CACHE
-- ======================

CREATE TABLE recommendation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    score DOUBLE,
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE
);

-- =========================================================
-- TEST DATA
-- =========================================================

-- USERS
INSERT INTO user (username, password, role) VALUES
('student1','password123','STUDENT'),
('faculty1','password123','FACULTY'),
('company1','password123','COMPANY');

-- STUDENT
INSERT INTO student (user_id, index_number, faculty, year)
VALUES (1,'2023/001','Elektrotehnicki fakultet',3);

-- COMPANY
INSERT INTO company (user_id, name, description, active)
VALUES (3,'Tech Solutions','Firma koja nudi IT prakse',TRUE);

-- INTERNSHIPS
INSERT INTO internship (company_id, title, description, technologies, period, conditions)
VALUES
(1,'Java Backend Internship','Rad na Spring Boot projektima','Java, Spring Boot, MySQL','Jun-Sep 2026','Poznavanje osnova Jave'),
(1,'Frontend Internship','Rad na Angular aplikacijama','Angular, HTML, CSS','Jul-Sep 2026','Osnove JavaScript-a');

-- CV
INSERT INTO cv (student_id, summary)
VALUES (1,'Student sa iskustvom u Java i AI projektima.');

-- SKILLS
INSERT INTO skill (student_id, name)
VALUES (1,'Java'),
       (1,'Spring Boot');

-- INTERESTS
INSERT INTO interest (student_id, name)
VALUES (1,'Backend Development'),
       (1,'Artificial Intelligence');

-- EDUCATION
INSERT INTO education (student_id, institution, degree, start_year, end_year)
VALUES (1,'Elektrotehnicki fakultet','Bachelor of Computer Science',2023,2027);

-- WORK EXPERIENCE
INSERT INTO work_experience (student_id, company_name, description, start_date, end_date)
VALUES (1,'Local IT Firm','Rad na manjim web aplikacijama','2024-06-01','2024-09-01');

-- APPLICATION
INSERT INTO internship_application (student_id, internship_id)
VALUES (1,1);

-- WORK LOG
INSERT INTO work_log (student_id, internship_id, week_number, description)
VALUES
(1,1,1,'Ucenje osnova Spring Boot-a'),
(1,1,2,'Implementacija REST endpointa');

-- EVALUATION
INSERT INTO evaluation (student_id, internship_id, grade, comment)
VALUES (1,1,5,'Odlican napredak');
