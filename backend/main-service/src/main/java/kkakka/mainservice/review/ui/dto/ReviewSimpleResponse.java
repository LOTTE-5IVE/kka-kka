package kkakka.mainservice.review.ui.dto;

import kkakka.mainservice.review.application.dto.ReviewDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewSimpleResponse {

    private Long id;
    private String contents;
    private Double rating;

    public static ReviewSimpleResponse create(ReviewDto reviewDto) {
        return new ReviewSimpleResponse(
                reviewDto.getId(),
                reviewDto.getContents(),
                reviewDto.getRating()
        );
    }
}
