package kkakka.mainservice.member.member.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private Long id;
    private String name;
    private String imageUrl;

    public static ProductResponse create(Long id, String name, String imageUrl) {
        return new ProductResponse(id, name, imageUrl);
    }
}
