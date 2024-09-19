-- 테이블 생성
CREATE TABLE departments
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_type VARCHAR(255)
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
