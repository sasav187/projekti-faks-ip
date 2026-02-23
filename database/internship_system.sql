DROP DATABASE IF EXISTS internship_system;
CREATE DATABASE internship_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE internship_system;

-- ======================
-- USERS
-- ======================

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT','FACULTY','COMPANY') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    changed_password_at TIMESTAMP NULL
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
    title VARCHAR(255) NOT NULL,
    description TEXT,
    period VARCHAR(255),
    conditions TEXT,
    capacity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);

-- ======================
-- TECHNOLOGY
-- ======================

CREATE TABLE technology (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE internship_technology (
    internship_id BIGINT,
    technology_id BIGINT,
    PRIMARY KEY (internship_id, technology_id),
    FOREIGN KEY (internship_id) REFERENCES internship(id) ON DELETE CASCADE,
    FOREIGN KEY (technology_id) REFERENCES technology(id) ON DELETE CASCADE
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
-- SKILL
-- ======================

CREATE TABLE skill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE student_skill (
    student_id BIGINT,
    skill_id BIGINT,
    level ENUM('BEGINNER','INTERMEDIATE','ADVANCED'),
    PRIMARY KEY (student_id, skill_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE
);

-- ======================
-- INTEREST
-- ======================

CREATE TABLE interest (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
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
    application_message TEXT,
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
    application_id BIGINT NOT NULL,
    week_number INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (application_id) REFERENCES internship_application(id) ON DELETE CASCADE
);

-- ======================
-- EVALUATION
-- ======================

CREATE TABLE evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    evaluator_role ENUM('COMPANY','FACULTY'),
    grade INT CHECK (grade BETWEEN 1 AND 5),
    comment TEXT,
    evaluation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(student_id, internship_id, evaluator_role),
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
