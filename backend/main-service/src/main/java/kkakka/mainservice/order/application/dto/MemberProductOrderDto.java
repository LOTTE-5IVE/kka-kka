package kkakka.mainservice.order.application.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.ui.dto.CouponResponseDto;
import kkakka.mainservice.member.member.ui.dto.CouponResponse;
import kkakka.mainservice.member.member.ui.dto.ProductOrderResponse;
import kkakka.mainservice.member.member.ui.dto.ProductResponse;
import kkakka.mainservice.member.member.ui.dto.ReviewSimpleResponse;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberProductOrderDto {

    private Long id;
    private Integer price;
    private Integer discount;
    private Integer quantity;
    private Integer deleted;
    @Nullable
    private CouponResponseDto couponDto;
    private MemberProductDto productDto;
    @Nullable
    private MemberReviewDto reviewDto;

    public static MemberProductOrderDto create(
            ProductOrder productOrder,
            Product product,
            Review review,
            Coupon coupon
    ) {
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getDiscount(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                CouponResponseDto.create(coupon),
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getImageUrl()
                ),
                new MemberReviewDto(
                        review.getId(), review.getContents(), review.getRating(), review.getCreatedAt()
                )
        );
    }

    public static MemberProductOrderDto create(
            ProductOrder productOrder,
            Product product,
            Review review
    ) {
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getDiscount(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                null,
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getImageUrl()
                ),
                new MemberReviewDto(
                        review.getId(), review.getContents(), review.getRating(), review.getCreatedAt()
                ));
    }

    public static MemberProductOrderDto create (
            ProductOrder productOrder,
            Product product,
            Coupon coupon
    ) {
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getDiscount(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                CouponResponseDto.create(coupon),
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getImageUrl()
                ),
                null
        );
    }

    public static MemberProductOrderDto create(
            ProductOrder productOrder,
            Product product
    ) {
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getDiscount(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                null,
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getImageUrl()
                ),
                null
        );
    }

    public ProductOrderResponse toResponse() {
        if (reviewDto == null && couponDto == null) {
            return ProductOrderResponse.create(
                    this.id,
                    this.price,
                    this.discount,
                    this.quantity,
                    this.deleted,
                    null,
                    ProductResponse.create(
                            this.productDto.getId(),
                            this.productDto.getName(),
                            this.productDto.imageUrl
                    ),
                    null
            );
        }

        if (couponDto == null) {
            return ProductOrderResponse.create(
                this.id,
                this.price,
                this.discount,
                this.quantity,
                this.deleted,
                null,
                ProductResponse.create(
                    this.productDto.getId(),
                    this.productDto.getName(),
                    this.productDto.imageUrl
                ),
                ReviewSimpleResponse.create(
                    this.reviewDto.getId(),
                    this.reviewDto.getContents(),
                    this.reviewDto.getRating(),
                    this.reviewDto.createdAt
                )
            );
        }

        if (reviewDto == null) {
            return ProductOrderResponse.create(
                this.id,
                this.price,
                this.discount,
                this.quantity,
                this.deleted,
                CouponResponse.create(
                    this.couponDto.getId(),
                    this.couponDto.getName(),
                    this.couponDto.getPriceRule(),
                    this.couponDto.getRegisteredAt(),
                    this.couponDto.getPercentage(),
                    this.couponDto.getMaxDiscount()
                ),
                ProductResponse.create(
                    this.productDto.getId(),
                    this.productDto.getName(),
                    this.productDto.imageUrl
                ),
                null
            );
        }

        return ProductOrderResponse.create(
                this.id,
                this.price,
                this.discount,
                this.quantity,
                this.deleted,
                CouponResponse.create(
                    this.couponDto.getId(),
                    this.couponDto.getName(),
                    this.couponDto.getPriceRule(),
                    this.couponDto.getRegisteredAt(),
                    this.couponDto.getPercentage(),
                    this.couponDto.getMaxDiscount()
                ),
                ProductResponse.create(
                    this.productDto.getId(),
                    this.productDto.getName(),
                    this.productDto.imageUrl
                ),
                ReviewSimpleResponse.create(
                    this.reviewDto.getId(),
                    this.reviewDto.getContents(),
                    this.reviewDto.getRating(),
                    this.reviewDto.createdAt
                )
            );
    }

    @Getter
    @AllArgsConstructor
    private static class MemberProductDto {

        private Long id;
        private String name;
        private String imageUrl;
    }

    @Getter
    @AllArgsConstructor
    private static class MemberReviewDto {

        private Long id;
        private String contents;
        private Double rating;
        private LocalDateTime createdAt;
    }
}
