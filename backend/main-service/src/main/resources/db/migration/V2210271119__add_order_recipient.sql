ALTER TABLE `order`
    ADD COLUMN recipient_address VARCHAR(255);

ALTER TABLE `order`
    ADD COLUMN recipient_name VARCHAR(255);

ALTER TABLE `order`
    ADD COLUMN recipient_phone VARCHAR(255);

ALTER TABLE member
    ADD COLUMN member_provider_name VARCHAR(255);
