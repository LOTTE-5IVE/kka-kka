package kkakka.mainservice.member.auth.application.dto;

import kkakka.mainservice.member.member.domain.MemberProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialProviderCodeDto {

    private String code;
    private MemberProviderName memberProviderName;

    public static SocialProviderCodeDto create(String code, MemberProviderName memberProviderName) {
        return new SocialProviderCodeDto(code, memberProviderName);
    }
}