CREATE TABLE review
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    contents         VARCHAR(255)          NULL,
    created_at       datetime              NULL,
    member_id        BIGINT                NULL,
    product_order_id BIGINT                NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_PRODUCT_ORDER FOREIGN KEY (product_order_id) REFERENCES product_order (id);