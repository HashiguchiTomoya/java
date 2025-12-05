-- 権限の付与
GRANT ALL ON *.* TO 'root'@'localhost';
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
DELIMITER //
-- ストアドプロシージャが存在すれば削除
DROP PROCEDURE IF EXISTS InsertLargeData;
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
            ('モニター', 2, 30000),
            ('キーボード', 2, 15000),
            ('マウス', 2, 12000),
            ('イヤホン', 2, 5600);
            
            SET i = i +1;
	END WHILE;
	COMMIT;
    END//
DELIMITER ;

-- 大量のデータを売入プロシージャの呼び出し
CALL InsertLargeData();

-- データが挿入されたことを確認
SELECT COUNT(*) FROM products;

-- スロークエリログの有効化
SET GLOBAL slow_query_log = ON;
-- 1秒以上のクエリを記録するように設定
SET GLOBAL long_query_time = 1;

-- 現在の設定値の確認
SELECT @@slow_query_log, @@long_query_time;

-- 性能測定のためのクエリ実行
-- priceが50000以上の商品を検索
SELECT * FROM products WHERE price >= 50000;
-- EXPLAIN:どのインデックスを使って(使わずにテーブルスキャンで)クエリを処理するかの結果
EXPLAIN SELECT * FROM products WHERE price >= 50000;

-- category_idごとに商品をカウント
SELECT category_id, COUNT(*) FROM products GROUP BY category_id;
EXPLAIN SELECT category_id, COUNT(*) FROM products GROUP BY category_id;

-- インデックスのの最適化
-- パフォーマンス改善のためのインデックスを作成
-- 50000以上の商品の検索
DROP INDEX IF EXISTS idx_price ON products; 
CREATE INDEX idx_price ON products(price);
-- 商品のカウント
CREATE INDEX idx_name ON products(name);

-- 改善後に実行
SELECT * FROM products WHERE price >= 50000;
-- key列にidx_priceが表示されているか確認
EXPLAIN SELECT * FROM products WHERE price >= 50000;

SELECT category_id, COUNT(*) FROM products GROUP BY category_id;
EXPLAIN SELECT category_id, COUNT(*) FROM products GROUP BY category_id;
