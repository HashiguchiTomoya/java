DROP DATABASE IF EXISTS complex_queries;
CREATE DATABASE complex_queries;
USE complex_queries;

SET FOREIGN_KEY_CHECKS = 0;
DROP table IF EXISTS customers;
DROP table IF EXISTS category;
DROP table IF EXISTS goods;
DROP table IF EXISTS sales;
DROP table IF EXISTS stocks;
SET FOREIGN_KEY_CHECKS = 1;

-- 顧客テーブルの作成
create table customers
(
    customerId INT AUTO_INCREMENT comment '顧客ID',
    name TEXT comment '顧客名',
    PRIMARY KEY(customerId)
)DEFAULT CHARSET = utf8mb4;

-- カテゴリーテーブルの作成
create table category
(
    categoryId INT AUTO_INCREMENT comment 'カテゴリーID',
    categoryName TEXT comment 'カテゴリー名',
    PRIMARY KEY(categoryId)
)DEFAULT CHARSET = utf8mb4;

-- 商品テーブルの作成
create table goods
(
    goodsId INT AUTO_INCREMENT comment '商品ID',
    goodsName TEXT comment '商品名',
    categoryId INT comment 'カテゴリーID',
    price INT comment '価格',
    PRIMARY KEY(goodsId),
    FOREIGN KEY(categoryId) REFERENCES category(categoryId)
)DEFAULT CHARSET = utf8mb4;

-- 注文テーブル
create table sales
(
    saleId INT AUTO_INCREMENT comment '売上ID',
    customerId INT comment '顧客ID',
    goodsId INT comment '商品ID',
    quantity INT comment '注文数',
    totalPrice INT comment '注文金額',
    saleDate DATE comment '注文日',
    PRIMARY KEY(saleId),
    FOREIGN KEY(customerId) REFERENCES customers(customerId),
    FOREIGN KEY(goodsId) REFERENCES goods(goodsId)
)DEFAULT CHARSET = utf8mb4;

-- 在庫テーブル
create table stocks
(
    goodsId INT comment '商品ID',
    stockQuantity INT comment '在庫数',
    PRIMARY KEY(goodsId),
    FOREIGN KEY(goodsId) REFERENCES goods(goodsId)
);

-- カテゴリの定義
INSERT INTO category(categoryName)VALUES('お菓子');
INSERT INTO category(categoryName)VALUES('文房具');

-- サンプルデータ(顧客名)
INSERT INTO customers(name)VALUES('田中商店');
INSERT INTO customers(name)VALUES('駄菓子屋まつだ');
INSERT INTO customers(name)VALUES('山本屋');

-- 商品の追加(カテゴリー１:お菓子)
INSERT INTO goods(goodsName, categoryId, price)VALUES('チョコレート', 1, 200);
INSERT INTO goods(goodsName, categoryId, price)VALUES('クッキー', 1, 120);
INSERT INTO goods(goodsName, categoryId, price)VALUES('ポテトチップス', 1, 150);
-- 商品の追加(カテゴリー2：文房具)
INSERT INTO goods(goodsName, categoryId, price)VALUES('えんぴつ', 2, 100);
INSERT INTO goods(goodsName, categoryId, price)VALUES('消しゴム', 2, 110);
INSERT INTO goods(goodsName, categoryId, price)VALUES('ノート', 2, 220);

-- 注文の追加(顧客ID：1)
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(1, 1, 200, (SELECT price FROM goods WHERE goodsId = 1) * 200, '2025-10-05');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(1, 3, 250, (SELECT price FROM goods WHERE goodsId = 3) * 250, '2025-10-05');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(1, 4, 250, (SELECT price FROM goods WHERE goodsId = 4) * 250, '2025-10-07');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(1, 6, 150, (SELECT price FROM goods WHERE goodsId = 6) * 150, '2025-10-07');
-- 売り上げの追加(顧客ID：2)
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(2, 1, 200, (SELECT price FROM goods WHERE goodsId = 1) * 200, '2025-10-10');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(2, 2, 300, (SELECT price FROM goods WHERE goodsId = 2) * 300, '2025-10-10');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(2, 4, 200, (SELECT price FROM goods WHERE goodsId = 4) * 200, '2025-10-11');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(2, 5, 200, (SELECT price FROM goods WHERE goodsId = 5) * 200, '2025-10-11');
-- 売り上げの追加(顧客ID：3)
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(3, 2, 250, (SELECT price FROM goods WHERE goodsId = 2) * 250, '2025-10-15');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(3, 3, 350, (SELECT price FROM goods WHERE goodsId = 3) * 350, '2025-10-15');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(3, 4, 200, (SELECT price FROM goods WHERE goodsId = 4) * 200, '2025-10-20');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(3, 5, 250, (SELECT price FROM goods WHERE goodsId = 5) * 250, '2025-10-22');
INSERT INTO sales(customerId, goodsId, quantity, totalPrice, saleDate)
VALUES(3, 6, 300, (SELECT price FROM goods WHERE goodsId = 6) * 300, '2025-10-22');

-- 月別売り上げランキング(クリエ)
SELECT -- 最終的にどの列を表示するか、計算結果を表示するかを指定
    -- saleDate列を'Y-%mの形式にフォーマットかして、月で選択
    DATE_FORMAT(saleDate,'%Y-%m') AS 月,
    -- 各グループのtotalPriceを合計を計算
    SUM(totalPrice) AS 月の売上,
    -- 関数rankで合計を降順(DESC)に並べた順位を計算して、MonthRankingという別名を付加
    RANK() OVER(ORDER BY SUM(totalPrice) DESC) AS 月別売上ランキング
FROM -- データを棕櫚くする対象テーブルを指定
    -- salesテーブルからデータを取得
    sales
GROUP BY -- 取得した行を指定した列の値でグループ化
    -- 結果を年-月ごとにグループ化
    月
ORDER BY -- 最終結果を、していた列の順序で並び替える
    -- 最終的な結果をsaleMonthの降順(新しい月が先頭)で並び替え
    月 DESC;
    
-- 商品別売上ランキング
SELECT
    -- goodsテーブルのgoodsName列を商品名という別名で選択
    g.goodsName AS 商品名,
    -- salesのquantityの合計を計算
    SUM(s.quantity) AS 販売数,
    -- salesのtotalPriceの合計を計算
    SUM(s.totalPrice) AS 総売上金額,
    -- 総売上金額を降順に並べた順位を計算
    RANK() OVER(ORDER BY SUM(s.totalPrice) DESC) AS 商品別売上ランキング
FROM
    -- salesをsで使用
    sales s
JOIN
    -- goodsをgで使用しsalesテーブルと結合する(結合条件はgoodsIdが一致すること)
    goods g ON s.goodsId = g.goodsId
GROUP BY
    -- 結果をgoodsIdとgoodsNameごとにグループ化(集計)
    g.goodsId, g.goodsName
ORDER BY
    -- 最終的な結果を総売上金額の降順で並び替え
    総売上金額 DESC;
    
-- カテゴリー別売上ランキング
SELECT
    -- categoryテーブルのcategoryName列をカテゴリー名で選択
    c.categoryName AS カテゴリー名,
    -- salesのtotalPriceの合計を計算
    SUM(s.totalPrice) AS 総売上金額,
    -- 総売上金額を降順に並べた順位を計算
    RANK() OVER(ORDER BY SUM(s.totalPrice) DESC) AS カテゴリー別売上ランキング
FROM
    -- salesをsで使用
    sales s
JOIN
    -- goodsをgで使用し、salesテーブルと結合する
    goods g ON s.goodsId = g.goodsId
JOIN
    -- categoryをcで使用しgoodsテーブルと結合
    category c ON g.categoryId = c.categoryId
GROUP BY
    -- 結果をcategoryIdとcategoryNameごとにグループ化
    c.categoryId, c.categoryName
ORDER BY
    -- 最終的な結果を総売上金額の降順で並び替え
    総売上金額 DESC;
    
-- 在庫のサンプルデータ
INSERT INTO stocks(goodsId, stockQuantity) VALUES(1, 500);
INSERT INTO stocks(goodsId, stockQuantity) VALUES(2, 400);
INSERT INTO stocks(goodsId, stockQuantity) VALUES(3, 300);
INSERT INTO stocks(goodsId, stockQuantity) VALUES(4, 200);
INSERT INTO stocks(goodsId, stockQuantity) VALUES(5, 100);
INSERT INTO stocks(goodsId, stockQuantity) VALUES(6, 50);

-- 在庫不足商品一覧
SELECT
    -- goodsテーブルのgoodsName列を商品名で選択
    g.goodsName AS 商品名,
    -- stocksテーブルのstockQuantity列を現在の在庫数で選択
    s.stockQuantity AS 現在の在庫数
FROM
    -- stocksをsで使用
    stocks s
JOIN
    -- goodsをgで使用しstocksテーブルと結合
    goods g ON s.goodsId = g.goodsId
WHERE -- 結合されたテーブルから、特定の条件に一致する行を絞り込む
    -- 抽出条件として在庫数が100未満を対象としている
    S.stockQuantity < 100 
ORDER BY
    -- 結果を在庫数の昇順(ASC)で並び替えて表示
    s.stockQuantity ASC;
    
-- 複雑な検索
SELECT
    c.name AS 顧客名,
    s.saleDate AS 注文日,
    s.totalPrice AS 注文金額,
    g.goodsName AS 商品名,
    cat.categoryName AS カテゴリー
FROM
    sales s
JOIN
    customers c ON s.customerId = c.customerId
JOIN
    goods g ON s.goodsId = g.goodsId
JOIN
    category cat ON g.categoryId = cat.categoryId
WHERE
    -- 10/1～10/31までのカテゴリーがお菓子でtotalPriceが10000以上
    s.saleDate >= '2025-10-01' AND s.saleDate <= '2025-10-31'
    AND cat.categoryName = 'お菓子'
    AND s.totalPrice >= 40000;