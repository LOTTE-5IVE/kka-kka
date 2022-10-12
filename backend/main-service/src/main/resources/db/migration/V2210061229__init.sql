CREATE TABLE IF NOT EXISTS `member`
(
    `id`                   bigint NOT NULL AUTO_INCREMENT,
    `address`              varchar(255) DEFAULT NULL,
    `age_group`            varchar(255) DEFAULT NULL,
    `email`                varchar(255) DEFAULT NULL,
    `grade`                varchar(255) DEFAULT NULL,
    `name`                 varchar(255) DEFAULT NULL,
    `phone`                varchar(255) DEFAULT NULL,
    `member_provider_name` varchar(255) DEFAULT NULL,
    `provider_id`          varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `cart`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `member_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKix170nytunweovf2v9137mx2o` (`member_id`),
    CONSTRAINT `FKix170nytunweovf2v9137mx2o` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `category`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `product`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT,
    `detail_image_url`   varchar(255)          DEFAULT NULL,
    `discount`           int                   DEFAULT '0',
    `image_url`          varchar(255)          DEFAULT NULL,
    `name`               varchar(255) NOT NULL,
    `nutrition_info_url` varchar(255)          DEFAULT NULL,
    `price`              int                   DEFAULT NULL,
    `registered_at`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `stock`              int                   DEFAULT NULL,
    `category_id`        bigint                DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
    CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `cart_item`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `coupon_id`  int    DEFAULT NULL,
    `price`      int    DEFAULT NULL,
    `quantity`   int    NOT NULL,
    `cart_id`    bigint DEFAULT NULL,
    `product_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
    KEY `FKjcyd5wv4igqnw413rgxbfu4nv` (`product_id`),
    CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
    CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;