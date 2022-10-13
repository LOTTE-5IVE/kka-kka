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
public class ReviewSimpleRespnse {

    private Long id;
    private String contents;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public static ReviewSimpleRespnse create(Long id, String contents, LocalDateTime createdAt) {
        return new ReviewSimpleRespnse(id, contents, createdAt);
    }
}
