package kkakka.mainservice.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchDto {

    private String keyword;

    public static SearchDto create(String keyword) {
        return new SearchDto(keyword);
    }
}
