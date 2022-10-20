CREATE TABLE nutrition
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    calorie       VARCHAR(255)          NULL,
    carbohydrate  VARCHAR(255)          NULL,
    sugar         VARCHAR(255)          NULL,
    protein       VARCHAR(255)          NULL,
    fat           VARCHAR(255)          NULL,
    saturated_fat VARCHAR(255)          NULL,
    trans_fat     VARCHAR(255)          NULL,
    cholesterol   VARCHAR(255)          NULL,
    sodium        VARCHAR(255)          NULL,
    calcium       VARCHAR(255)          NULL,
    CONSTRAINT pk_nutrition PRIMARY KEY (id)
);