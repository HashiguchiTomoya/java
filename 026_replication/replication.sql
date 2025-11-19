DROP DATABASE IF EXISTS example;
-- レプリケーション用のDB
CREATE DATABASE example;
USE example;

-- Slaveアカウントを作成
CREATE user 'repl'@'192.168.33.20' identified by 'SLave-password';
GRANT replication slave on *.* to 'repl'@'192.168.33.20';