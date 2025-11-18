DROP DATABASE IF EXISTS maintenance;
CREATE DATABASE maintenance;
USE maintenance;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS testData;
SET FOREIGN_KEY_CHECKS = 1;

-- 権限を付与するユーザーの作成
CREATE USER'user'@'localhost' IDENTIFIED BY 'pass word';
-- 権限を付加(selectとupdateの権限、.* :指定したすべてのテーブルを対象)
GRANT SELECT, UPDATE ON maintenance.* TO 'user'@'localhost';
-- 権限削除
-- REVOKE SELECT, UPDATE ON maintenance.* TO 'user'@'localhost';

-- サンプルデータテーブルの作成
CREATE TABLE testData
(
    id INT AUTO_INCREMENT COMMENT 'ID',
    name TEXT COMMENT '名前',
    age INT COMMENT '年齢',
    division TEXT COMMENT '部署',
    PRIMARY KEY(id)
)DEFAULT CHARSET = utf8mb4;

-- テーブルに値を代入
INSERT INTO testData(name, age, division) VALUES('山田太郎', '35', '営業');
INSERT INTO testData(name, age, division) VALUES('松本ななみ', '31', '経理');
INSERT INTO testData(name, age, division) VALUES('岡田泰三', '45', '管理');

-- DB上にバックアップなデータを作成
CREATE TABLE testData_backup AS
SELECT * FROM testData;

-- バックアップできているか確認
SELECT * FROM testData_backup;

-- 全レコードの削除
DELETE FROM testData;

-- バックアップテーブルを使用し元の状態に戻す
INSERT INTO testData
SELECT * FROM testData_backup;

-- 元に戻ったか確認
SELECT * FROM testData;

-- 全てのログの内容を表示(LIKE %log%でlogという文字が含まれるものを検索してSHOW VARIABLESで一覧表示)
SHOW VARIABLES LIKE '%log%';

-- パフォーマンス監視
SHOW GLOBAL STATUS;

-- プロファイリングを有効にする
SET profiling = 1;
-- 実行時間を測定したいクリエ
SELECT * FROM testData WHERE id = 3;
-- 結果を表示する
SHOW PROFILES;