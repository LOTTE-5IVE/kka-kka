CREATE TABLE member_coupon
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    member_id BIGINT                NULL,
    coupon_id BIGINT                NULL,
    is_used   BIT(1)                NULL,
    CONSTRAINT pk_membercoupon PRIMARY KEY (id)
);

ALTER TABLE member_coupon
    ADD CONSTRAINT FK_MEMBERCOUPON_ON_COUPONID FOREIGN KEY (coupon_id) REFERENCES coupon (id);

ALTER TABLE member_coupon
    ADD CONSTRAINT FK_MEMBERCOUPON_ON_MEMBERID FOREIGN KEY (member_id) REFERENCES member (id);