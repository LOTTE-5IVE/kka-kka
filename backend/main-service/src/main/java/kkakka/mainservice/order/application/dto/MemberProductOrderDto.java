package kkakka.mainservice.order.application.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
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
    private Integer quantity;
    private Integer deleted;
    @Nullable
    private CouponResponseDto couponDto;
    private MemberProductDto productDto;
    @Nullable
    private MemberReviewDto reviewDto;

    public static MemberProductOrderDto create(ProductOrder productOrder, Product product,
            Optional<Review> optionalReview) {
        if (optionalReview.isEmpty() && Objects.isNull(productOrder.getCoupon())) {
            return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                null,
                new MemberProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl(),
                    product.getDiscount()
                ),
                null
            );
        } else if (optionalReview.isEmpty()) {
            return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                CouponResponseDto.create(productOrder.getCoupon()),
                new MemberProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl(),
                    product.getDiscount()
                ), null);
        } else if (Objects.isNull(productOrder.getCoupon())) {
            Review review = optionalReview.get();
            return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                null,
                new MemberProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl(),
                    product.getDiscount()
                ),
                new MemberReviewDto(
                review.getId(), review.getContents(), review.getRating(), review.getCreatedAt()
            ));
        }
        Review review = optionalReview.get();
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                productOrder.getDeleted(),
                CouponResponseDto.create(productOrder.getCoupon()),
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl(),
                        product.getDiscount()
                ),
                new MemberReviewDto(
                        review.getId(), review.getContents(), review.getRating(), review.getCreatedAt()
                )
        );
    }

    public ProductOrderResponse toResponse() {
        if (Optional.ofNullable(reviewDto).isEmpty() && Optional.ofNullable(couponDto).isEmpty()) {
            return ProductOrderResponse.create(
                    this.id,
                    this.price,
                    this.quantity,
                    this.deleted,
                    null,
                    ProductResponse.create(
                            this.productDto.getId(),
                            this.productDto.getName(),
                            this.productDto.getPrice(),
                            this.productDto.imageUrl,
                            this.productDto.getDiscount()
                    ),
                    null
            );
        } else if(Optional.ofNullable(couponDto).isEmpty()) {
            return ProductOrderResponse.create(
                this.id,
                this.price,
                this.quantity,
                this.deleted,
                null,
                ProductResponse.create(
                    this.productDto.getId(),
                    this.productDto.getName(),
                    this.productDto.getPrice(),
                    this.productDto.imageUrl,
                    this.productDto.getDiscount()
                ),
                ReviewSimpleResponse.create(
                    this.reviewDto.getId(),
                    this.reviewDto.getContents(),
                    this.reviewDto.getRating(),
                    this.reviewDto.createdAt
                )
            );
        } else if(Optional.ofNullable(reviewDto).isEmpty()) {
            return ProductOrderResponse.create(
                this.id,
                this.price,
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
                    this.productDto.getPrice(),
                    this.productDto.imageUrl,
                    this.productDto.getDiscount()
                ),
                null
            );
        }
        return ProductOrderResponse.create(
                this.id,
                this.price,
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
                    this.productDto.getPrice(),
                    this.productDto.imageUrl,
                    this.productDto.getDiscount()
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
        private Integer price;
        private String imageUrl;
        private Integer discount;
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
