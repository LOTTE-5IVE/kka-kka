CREATE TABLE discount
(
    id            BIGINT       AUTO_INCREMENT NOT NULL,
    category_id   BIGINT       NULL,
    product_id    BIGINT       NULL,
    name          VARCHAR(255) NULL,
    discount      INT          NULL,
    started_at    datetime     NULL,
    expired_at    datetime     NULL,
    is_deleted    BIT(1)       NULL,
    discount_type VARCHAR(255) NULL,
    CONSTRAINT pk_discount PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4;

ALTER TABLE discount
    ADD CONSTRAINT FK_DISCOUNT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE discount
    ADD CONSTRAINT FK_DISCOUNT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);