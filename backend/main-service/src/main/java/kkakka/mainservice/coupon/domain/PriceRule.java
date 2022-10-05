package kkakka.mainservice.coupon.domain;

public enum PriceRule {
  DISCOUNT, GRADE_COUPON, COUPON;

  public boolean equals(String value) {
    return this.name().equals(value);
  }
}