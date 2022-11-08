package kkakka.mainservice.review.application;

import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.common.exception.NotFoundProductException;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.review.application.dto.MemberDto;
import kkakka.mainservice.review.application.dto.ReviewDto;
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductOrderRepository productOrderRepository;

    public Page<ReviewDto> showReviewsByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findAllByProductId(productId, pageable)
                .map(review -> ReviewDto.create(
                                review,
                                MemberDto.create(review.getMemberName())
                        )
                );
    }

    @Transactional
    public Long writeReview(Long memberId, Long productOrderId, ReviewRequest reviewRequest) {
        validateAlreadyWritten(memberId, productOrderId);

        final Review review = Review.create(
                reviewRequest.getContents(),
                reviewRequest.getRating(),
                memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new),
                productOrderRepository.findById(productOrderId).orElseThrow(
                        NotFoundProductException::new)
        );
        reviewRepository.save(review);

        updateProductRatingAvg(review.getProductOrder().getProduct());

        return review.getId();
    }

    private void validateAlreadyWritten(Long memberId, Long productId) {
        reviewRepository.findByMemberIdAndProductOrderId(memberId, productId)
                .ifPresent(review -> {
                    throw new KkaKkaException();
                });
    }

    private void updateProductRatingAvg(Product product) {
        final Double ratingAvg = reviewRepository.findRatingAvgByProductId(product.getId())
                .orElse(0.0);
        product.updateRatingAvg(ratingAvg);
    }

    public int countReviews(Long productId) {
        return reviewRepository.countAllByProductId(productId);
    }
}
