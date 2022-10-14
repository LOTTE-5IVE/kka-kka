package kkakka.mainservice.product.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchWords {

    private final List<String> searchWords;

    public static SearchWords create(String keyword) {
        if (keyword == null) {
            return new SearchWords(new ArrayList<>());
        }

        return new SearchWords(List.of(keyword.split("\\s")));
    }
}
