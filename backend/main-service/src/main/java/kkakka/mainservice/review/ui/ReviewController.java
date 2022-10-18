package kkakka.mainservice.review.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.dto.PageableResponse;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.review.application.ReviewService;
import kkakka.mainservice.review.application.dto.ReviewDto;
import kkakka.mainservice.review.ui.dto.MemberSimpleResponse;
import kkakka.mainservice.review.ui.dto.ReviewRequest;
import kkakka.mainservice.review.ui.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<PageableResponse<List<ReviewResponse>>> showReviews(
            @PageableDefault(size = 6, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
            @RequestParam(value = "product") Long productId
    ) {
        final Page<ReviewDto> reviewDtos = reviewService.showReviewsByProductId(productId,
                pageable);
        final List<ReviewResponse> reviews = reviewDtos.stream()
                .map(reviewDto -> ReviewResponse.create(
                        reviewDto,
                        MemberSimpleResponse.create(reviewDto.getMemberName())
                )).collect(Collectors.toList());

        final PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                reviewDtos.getTotalPages(),
                pageable.getPageSize(),
                reviewDtos.getTotalElements()
        );

        return ResponseEntity.ok().body(PageableResponse.from(reviews, pageInfo));
    }

    @MemberOnly
    @PostMapping
    public ResponseEntity<Void> writeReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(value = "productOrder") Long productOrderId,
            @RequestBody ReviewRequest reviewRequest
    ) {
        final Long reviewId = reviewService.writeReview(loginMember.getId(), productOrderId,
                reviewRequest);
        return ResponseEntity.created(URI.create(reviewId.toString())).build();
    }
}
