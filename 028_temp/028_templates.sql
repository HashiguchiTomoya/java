CREATE DATABASE IF NOT EXISTS templates;
USE templates;
CREATE TABLE IF NOT EXISTS users
(
	userId INT AUTO_INCREMENT COMMENT 'ユーザーID',
    username TEXT NOT NULL COMMENT 'ユーザー名',
    password VARCHAR(128) NOT NULL COMMENT 'パスワード',
    -- 値が入力されなかったらデフォルトでuserを入力
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    PRIMARY KEY(userId)
)DEFAULT CHARSET utf8mb4;