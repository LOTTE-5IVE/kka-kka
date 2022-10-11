package kkakka.mainservice.review.application;

import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.review.application.dto.MemberDto;
import kkakka.mainservice.review.application.dto.ReviewDto;
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<ReviewDto> showReviewsByProductId(Long productId) {
        final List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviews.stream()
                .map(review -> ReviewDto.create(review, MemberDto.create(review.getMemberName())))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long writeReview(Long memberId, Long productId, ReviewRequest reviewRequest) {
        validateAlreadyWrittenReview(memberId, productId);

        final Review review = Review.create(
                reviewRequest.getContents(),
                memberRepository.findById(memberId).orElseThrow(),
                productRepository.findById(productId).orElseThrow()
        );
        reviewRepository.save(review);
        return review.getId();
    }

    private void validateAlreadyWrittenReview(Long memberId, Long productId) {
        reviewRepository.findByMemberIdAndProductId(memberId, productId)
                .ifPresent(review -> {
                    throw new KkaKkaException();
                });
    }
}
