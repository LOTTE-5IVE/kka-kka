package kkakka.mainservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableNoOffsetResponse<T> {

    @JsonProperty(value = "data")
    private T responseDto;
    @JsonProperty(value = "pageInfo")
    private NoOffsetPageInfo pageInfo;

    public static <T> PageableNoOffsetResponse<T> from(T responseDto, NoOffsetPageInfo pageInfo) {
        return new PageableNoOffsetResponse<>(responseDto, pageInfo);
    }
}
