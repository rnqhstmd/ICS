-- 테이블 생성
CREATE TABLE departments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_type VARCHAR(255)
);

CREATE TABLE parts
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_type    VARCHAR(255) NOT NULL,
    department_id BIGINT,
    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id)
);

-- 부서 데이터 삽입
INSERT INTO departments (department_type)
VALUES ('AI');
INSERT INTO departments (department_type)
VALUES ('SYSTEM');
INSERT INTO departments (department_type)
VALUES ('ENERGY');
INSERT INTO departments (department_type)
VALUES ('MEDIA');
INSERT INTO departments (department_type)
VALUES ('SECURITY');
INSERT INTO departments (department_type)
VALUES ('LAB');
INSERT INTO departments (department_type)
VALUES ('MANAGEMENT');

-- AI 부서 파트 삽입 (department_id = 1)
INSERT INTO parts (part_type, department_id) VALUES ('AI_EX', 1);
INSERT INTO parts (part_type, department_id) VALUES ('AI_HC', 1);
INSERT INTO parts (part_type, department_id) VALUES ('AI_VA', 1);
INSERT INTO parts (part_type, department_id) VALUES ('AI_SUP', 1);

-- SYSTEM 부서 파트 삽입 (department_id = 2)
INSERT INTO parts (part_type, department_id) VALUES ('SYS_EX', 2);
INSERT INTO parts (part_type, department_id) VALUES ('SYS_OSS', 2);
INSERT INTO parts (part_type, department_id) VALUES ('SYS_BSS', 2);
INSERT INTO parts (part_type, department_id) VALUES ('SYS_CNC', 2);
INSERT INTO parts (part_type, department_id) VALUES ('SYS_COM', 2);

-- ENERGY 부서 파트 삽입 (department_id = 3)
INSERT INTO parts (part_type, department_id) VALUES ('EN_EX', 3);
INSERT INTO parts (part_type, department_id) VALUES ('EN_ZN', 3);
INSERT INTO parts (part_type, department_id) VALUES ('EN_SV', 3);
INSERT INTO parts (part_type, department_id) VALUES ('EN_PF', 3);
INSERT INTO parts (part_type, department_id) VALUES ('EN_PD', 3);

-- MEDIA 부서 파트 삽입 (department_id = 4)
INSERT INTO parts (part_type, department_id) VALUES ('MD_EX', 4);
INSERT INTO parts (part_type, department_id) VALUES ('MD_PF', 4);
INSERT INTO parts (part_type, department_id) VALUES ('MD_BNS_1', 4);
INSERT INTO parts (part_type, department_id) VALUES ('MD_BNS_2', 4);

-- SECURITY 부서 파트 삽입 (department_id = 5)
INSERT INTO parts (part_type, department_id) VALUES ('SC_EX', 5);
INSERT INTO parts (part_type, department_id) VALUES ('SC_SL', 5);
INSERT INTO parts (part_type, department_id) VALUES ('SC_IFR', 5);
INSERT INTO parts (part_type, department_id) VALUES ('SC_DEV', 5);

-- LAB 부서 파트 삽입 (department_id = 6)
INSERT INTO parts (part_type, department_id) VALUES ('LAB_RS_1', 6);
INSERT INTO parts (part_type, department_id) VALUES ('LAB_RS_2', 6);

-- MANAGEMENT 부서 파트 삽입 (department_id = 7)
INSERT INTO parts (part_type, department_id) VALUES ('MAN_SUP', 7);
INSERT INTO parts (part_type, department_id) VALUES ('MAN_LAB', 7);
