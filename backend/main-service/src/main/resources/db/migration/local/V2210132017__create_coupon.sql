CREATE TABLE coupon
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    category_id     BIGINT                NULL,
    grade           VARCHAR(255)          NULL,
    product_id      BIGINT                NULL,
    name            VARCHAR(255)          NULL,
    detail          VARCHAR(255)          NULL,
    price_rule      VARCHAR(255)          NULL,
    registered_at   datetime              NULL,
    started_at      datetime              NULL,
    expired_at      datetime              NULL,
    percentage      INT                   NULL,
    max_discount    INT                   NULL,
    min_order_price INT                   NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
);

ALTER TABLE coupon
    ADD CONSTRAINT FK_COUPON_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE coupon
    ADD CONSTRAINT FK_COUPON_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE coupon ADD is_deleted BIT(1);