-- 権限の付与
GRANT SUPER ON *.* TO 'root'@'localhost';
-- 変更を有効にする
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS performance;
USE performance;


CREATE TABLE IF NOT EXISTS products
(
    product_id INT AUTO_INCREMENT,
    name VARCHAR(100),
    category_id INT,
    price DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(product_id)
)DEFAULT CHARSET utf8mb4;

-- 大量のサンプルデータの挿入のためのストアドプロシージャ
-- DELIMITER //
CREATE PROCEDURE InsertLargeData()
BEGIN
    DECLARE i INT DEFAULT 0;
    -- ループ回数を指定(1000)
    DECLARE max_loops INT DEFAULT 1000;
    
    START TRANSACTION;
    
    WHILE i < max_loops DO
        INSERT INTO products(name, category_id, price)
        VALUES
            ('ノートパソコン', 1, 98000),
            ('スマートフォン', 1, 89000),
            ('モニター', 1, 30000),
            ('キーボード', 2, 15000),
            ('マウス', 2, 12000),
            ('イヤホン',2, ,5600);
            
            SET i = i +1;
        END WHILE;
        COMMIT;
    END//
-- DELIMITER ;

-- 大量のデータを売入プロシージャの呼び出し
CALL InsertLargeData();

-- データが挿入されたことを確認
SELECT COUNT(*) FROM products;

-- DROP PROCEDURE InsertLargeDate();

-- スロークエリログの有効化 (MySQLサーバーが動作している間のみ有効)
SET GLOBAL slow_query_log = ON;

-- 1秒以上のクエリを記録するように設定
-- 0.5秒を試してエラーが出る場合は、1秒(整数)で試すのが確実です。
SET GLOBAL long_query_time = 1;

-- 現在の設定値の確認
SELECT @@slow_query_log, @@long_query_time;
