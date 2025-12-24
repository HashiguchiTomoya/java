CREATE DATABASE IF NOT EXISTS BulletiBoard;
USE bulletiBoard;
CREATE TABLE IF NOT EXISTS board
(
	id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL DEFAULT '名無し',
    comment text NOT NULL DEFAULT 'コメントなし',
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    -- 親コメントのIDを格納するためのカラム
    pareniId INT NULL DEFAULT 0,
    -- 返信レベルを格納するカラム
    threadLevel INT NULL DEFAULT 0
)DEFAULT CHARSET utf8mb4;

SELECT * FROM board;
INSERT INTO board(name, comment) VALUE (?, ?);