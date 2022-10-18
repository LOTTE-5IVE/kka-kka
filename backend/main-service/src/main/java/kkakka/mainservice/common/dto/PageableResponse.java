package kkakka.mainservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableResponse<T> {

    @JsonProperty(value = "data")
    private T responseDto;
    private PageInfo pageInfo;

    public static <T> PageableResponse<T> from(T responseDto, PageInfo pageInfo) {
        return new PageableResponse<>(responseDto, pageInfo);
    }
}
