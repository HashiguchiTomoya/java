DROP DATABASE IF EXISTS student_manager;
CREATE DATABASE student_manager;
USE student_manager;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS grandes;
SET FOREIGN_KEY_CHECKS = 1;

-- 教員テーブル
CREATE TABLE teachers
(
    teacherId INT comment '教員番号',
    teacherName TEXT comment'教員名',
    PRIMARY KEY(teacherId)
)DEFAULT CHARSET = utf8mb4;

-- コーステーブル
CREATE TABLE courses
(
    courseId INT AUTO_INCREMENT comment 'コースID',
    courseName TEXT comment 'コース名',
    teacherId INT comment '教員番号(FK)',
    units INT comment '単位数',
    PRIMARY KEY(courseId),
    FOREIGN KEY(teacherId) REFERENCES teachers(teacherId)
)DEFAULT CHARSET = utf8mb4;

-- 学生テーブル
CREATE TABLE students
(
    studentId INT comment '学籍番号',
    studentName TEXT comment '学生名',
    courseId INT comment 'コースID',
    PRIMARY KEY(studentId),
    FOREIGN KEY(courseId) REFERENCES courses(courseId)
)DEFAULT CHARSET = utf8mb4;

-- 成績テーブル
CREATE TABLE grandes
(
    grandeId INT AUTO_INCREMENT comment '成績ID',
    studentId INT comment '学籍番号(FK)',
    courseId INT comment 'コースID(FK)',
    sum INT comment '点数',
    grade char(1) comment '評価',
    PRIMARY KEY(grandeId),
    FOREIGN KEY(studentId) REFERENCES students(studentId),
    FOREIGN KEY(courseId) REFERENCES courses(courseId)
)DEFAULT CHARSET = utf8mb4;

-- 教員
INSERT INTO teachers(teacherId, teacherName)
VALUES
    ('101', '湯川浩紀'),
    ('102', '岸谷薫');

-- コース
INSERT INTO courses(courseName, teacherId, units)
VALUES
    ('物理学','101','3'),
    ('現代文','102','3');

-- 学生
INSERT INTO students(studentId, studentName, courseId)
VALUES
('001', '佐藤翔太', 1),
('002', '山崎太一', 2),
('003', '岡本夏樹', 1),
('004', '原田るみ', 2);

-- デリミタの変更
-- 終わりを意味する;を//に変更する(スクリプト内で複数回;を使うため混乱を避ける)
DELIMITER //

DROP PROCEDURE IF EXISTS calculation_grade//

-- 成績ストアドプロジャー
CREATE PROCEDURE calculation_grade()

BEGIN
    UPDATE grandes
    SET grade = CASE
        WHEN sum >= 90 THEN 'A'
        WHEN sum >= 80 THEN 'B'
        WHEN sum >= 70 THEN 'C'
        WHEN sum >= 60 THEN 'D'
        WHEN sum >= 50 THEN 'E'
        ELSE 'F'
    END;
END
//

-- デリミタを元に戻す
DELIMITER ;

-- 履修登録トリガー
DELIMITER //

DROP TRIGGER IF EXISTS course_registration//

-- 新しいトリガーを作成
CREATE TRIGGER course_registration
-- studentsテーブルに新しい行が挿入された後にこのトリガーを起動
AFTER INSERT ON students
-- 挿入された行ごとに処理を実行
FOR EACH ROW
BEGIN
    -- gradesテーブルに新しい行を挿入
    INSERT INTO grandes(studentId, courseId, sum, grade)
    -- NEW.studentId:新しく挿入されたstudentsテーブルから値を取得
    VALUES(NEW.studentId, NEW.courseId,0, '');
END;
//

DELIMITER ;

-- トリガー動作確認用サンプル
INSERT INTO students(studentId, studentName, courseId)
VALUES(005, 'サンプル学生', 1);
SELECT * FROM grandes

-- 成績分析ビュー
DELIMITER //

DROP VIEW IF EXISTS grade_view//

-- 新しいビューを作成
CREATE VIEW grade_view AS 
SELECT 
    s.studentId, 
    s.studentName, 
    g.grade AS grade
FROM students s
JOIN grandes g ON s.studentId = g.studentId;
//
DERIMITER ;

-- ビューの確認
SELECT * FROM grade_view;