package kkakka.mainservice.review.application.dto;

import java.time.LocalDateTime;
import kkakka.mainservice.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private MemberDto memberDto;

    public static ReviewDto create(Review review, MemberDto memberDto) {
        return new ReviewDto(review.getId(), review.getContents(), review.getCreatedAt(),
                memberDto);
    }

    public String getMemberName() {
        return memberDto.getName();
    }
}
