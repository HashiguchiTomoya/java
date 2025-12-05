-- ユーザーの作成
DROP USER 'repl'@'%';
CREATE USER 'repl'@'%' IDENTIFIED BY 'password';
-- 作成したユーザーにレプリケーションのスレーブとしてふるまう権限を付与
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
-- 変更の反映
FLUSH PRIVILEGES;

-- マスターサーバーの現在のレプリケーションの状態を表示
SHOW MASTER STATUS;

-- スレーブサーバーへの接続設定
CHANGE MASTER TO
MASTER_HOST='10.170.180.104',
MASTER_USER='repl',
MASTER_PASSWORD='password',
MASTER_LOG_FILE='mysql-bin.000001',
MASTER_LOG_POS=107;

START SLAVE;

CREATE DATABASE IF NOT EXISTS replication;
USE replication;

CREATE TABLE IF NOT EXISTS products
(
	id INT AUTO_INCREMENT,
    name VARCHAR(100),
    price INT,
    PRIMARY KEY(id)
)DEFAULT CHARSET utf8mb4;