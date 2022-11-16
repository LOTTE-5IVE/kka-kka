package kkakka.mainservice.member.member.ui.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeSerializer;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public static ReviewSimpleResponse create(Long id, String contents, Double rating,
            LocalDateTime createdAt) {
        return new ReviewSimpleResponse(id, contents, rating, createdAt);
    }
}
