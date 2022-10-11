package kkakka.mainservice.review.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.review.application.ReviewService;
import kkakka.mainservice.review.application.dto.ReviewDto;
import kkakka.mainservice.review.ui.dto.MemberSimpleResponse;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import kkakka.mainservice.review.ui.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> showReviews(
            @RequestParam(value = "product") Long productId) {
        final List<ReviewDto> reviewDtos = reviewService.showReviewsByProductId(productId);
        final List<ReviewResponse> reviews = reviewDtos.stream()
                .map(reviewDto -> ReviewResponse.create(
                        reviewDto,
                        MemberSimpleResponse.create(reviewDto.getMemberName())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(reviews);
    }

    @MemberOnly
    @PostMapping
    public ResponseEntity<Void> writeReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(value = "product") Long productId,
            @RequestBody ReviewRequest reviewRequest
    ) {
        final Long reviewId = reviewService.writeReview(loginMember.getId(), productId,
                reviewRequest);
        return ResponseEntity.created(URI.create(reviewId.toString())).build();
    }
}
