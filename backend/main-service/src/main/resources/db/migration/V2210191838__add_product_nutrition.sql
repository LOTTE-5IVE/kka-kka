ALTER TABLE product ADD COLUMN nutrition_id BIGINT;

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_NUTRITION FOREIGN KEY (nutrition_id) REFERENCES nutrition (id);