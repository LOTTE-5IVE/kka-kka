package kkakka.mainservice.review.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSimpleResponse {

    private String name;

    public static MemberSimpleResponse create(String name) {
        return new MemberSimpleResponse(name);
    }
}
