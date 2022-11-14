ALTER TABLE `kkakka`.`product_order` ADD `coupon_id` BIGINT;

ALTER TABLE product_order
    ADD CONSTRAINT FK_PRODUCT_ORDER_ON_COUPON FOREIGN KEY (coupon_id) REFERENCES coupon (id);