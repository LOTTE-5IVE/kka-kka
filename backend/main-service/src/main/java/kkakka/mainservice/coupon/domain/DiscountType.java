package kkakka.mainservice.coupon.domain;

public enum DiscountType {
    PRODUCT_DISCOUNT, CATEGORY_DISCOUNT;

    public boolean equals(String value) {
        return this.name().equals(value);
    }
}
