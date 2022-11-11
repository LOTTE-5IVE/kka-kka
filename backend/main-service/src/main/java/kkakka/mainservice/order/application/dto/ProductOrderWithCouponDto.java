package kkakka.mainservice.order.application.dto;

import kkakka.mainservice.cart.ui.dto.CouponDto;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderWithCouponDto {

    private Long id;
    private Long productId;
    private String name;
    private Integer discount;
    private String imageUrl;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer totalDiscount;
    private CouponDto couponDto;

    public static ProductOrderWithCouponDto create(Integer quantity, Product product,
        Integer discountedPrice, Coupon coupon) {
        return new ProductOrderWithCouponDto(
            null,
            product.getId(),
            product.getName(),
            product.getDiscount(),
            product.getImageUrl(),
            quantity,
            product.getPrice(),
            product.getPrice() * quantity,
            discountedPrice * quantity,
            (product.getPrice() * quantity) - (discountedPrice * quantity),
            CouponDto.toDto(coupon)
        );
    }

}
