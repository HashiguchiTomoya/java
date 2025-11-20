-- もしcreate_tableが存在するなら完全に削除(IF EXISTS:存在する場合のみ)
DROP DATABASE IF EXISTS create_tables;
CREATE DATABASE create_tables;
USE create_tables;

-- 外部キーの制約のチェックを一時的に無効
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS categorys;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orderData;
-- 無効にした外部キー制約のチェックを有効
SET FOREIGN_KEY_CHECKS = 1;

-- ユーザーテーブルの作成(AUTO_INCREMENT：自動的に値を1ずつ増やす)
create table users
(
    userId INT AUTO_INCREMENT comment 'ユーザーID',
    name TEXT comment '名前',
    email VARCHAR(100) comment 'メール',
    password VARCHAR(10) comment 'パスワード',
    postcode VARCHAR(7) comment '郵便番号',
    address VARCHAR(100) comment '住所',
    phone VARCHAR(11) comment '電話番号',
    PRIMARY KEY(userId)
) DEFAULT CHARSET = utf8mb4;

-- カテゴリーテーブルの作成
create table categorys
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
    goodsStock INT comment '在庫',
    goodsprice INT comment '価格',
    PRIMARY KEY(goodsId),
    FOREIGN KEY(categoryId) REFERENCES categorys(categoryId)
)DEFAULT CHARSET = utf8mb4;

-- 注文テーブルの作成
create table orders
(
    orderId INT AUTO_INCREMENT comment '注文ID ',
    userId INT comment 'ユーザーID',,
    orderDate DATE comment '注文日',
    postage INT comment '送料',
    amount INt comment = '金額',
    PRIMARY KEY(orderId),
    FOREIGN KEY(userId) REFERENCES users(userId),
    FOREIGN KEY(goodsId) REFERENCES goods(goodsId)
)DEFAULT CHARSET = utf8mb4;

-- 注文詳細
create table orderData
(
    dataId INT AUTO_INCREMENT comment '詳細ID',
    orderId INT comment '注文ID',
    goodsId INT comment '商品ID',
    quantity INT comment '注文数',
    subtotal INT comment '小計',
    PRIMARY KEY(dataId),
    FOREIGN KEY(orderId) REFERENCES orders(orderId),
    FOREIGN KEY(goodsId) REFERENCES goods(goods)
)DEFAULT CHARSET = utf8mb4;

-- インデックスの作成
CREATE INDEX USERID ON create_tables.users(userId);
CREATE INDEX GOODSID ON create_tables.goods(goodsId);
