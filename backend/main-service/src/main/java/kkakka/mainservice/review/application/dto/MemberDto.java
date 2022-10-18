package kkakka.mainservice.review.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    private String name;

    public static MemberDto create(String name) {
        return new MemberDto(name);
    }
}
