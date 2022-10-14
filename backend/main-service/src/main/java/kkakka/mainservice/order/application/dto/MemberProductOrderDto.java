package kkakka.mainservice.order.application.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import kkakka.mainservice.member.member.ui.dto.ProductOrderResponse;
import kkakka.mainservice.member.member.ui.dto.ProductResponse;
import kkakka.mainservice.member.member.ui.dto.ReviewSimpleRespnse;
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
    private MemberProductDto productDto;
    @Nullable
    private MemberReviewDto reviewDto;

    public static MemberProductOrderDto create(ProductOrder productOrder, Product product,
            Optional<Review> optionalReview) {
        if (optionalReview.isEmpty()) {
            return new MemberProductOrderDto(
                    productOrder.getId(),
                    productOrder.getPrice(),
                    productOrder.getQuantity(),
                    new MemberProductDto(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            product.getDiscount()
                    ),
                    null
            );
        }

        Review review = optionalReview.get();
        return new MemberProductOrderDto(
                productOrder.getId(),
                productOrder.getPrice(),
                productOrder.getQuantity(),
                new MemberProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl(),
                        product.getDiscount()
                ),
                new MemberReviewDto(
                        review.getId(), review.getContents(), review.getCreatedAt()
                )
        );
    }

    public ProductOrderResponse toResponse() {
        if (Optional.ofNullable(reviewDto).isEmpty()) {
            return ProductOrderResponse.create(
                    this.id,
                    this.price,
                    this.quantity,
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
                ProductResponse.create(
                        this.productDto.getId(),
                        this.productDto.getName(),
                        this.productDto.getPrice(),
                        this.productDto.imageUrl,
                        this.productDto.getDiscount()
                ),
                ReviewSimpleRespnse.create(
                        this.reviewDto.getId(),
                        this.reviewDto.getContents(),
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
        private LocalDateTime createdAt;
    }
}
