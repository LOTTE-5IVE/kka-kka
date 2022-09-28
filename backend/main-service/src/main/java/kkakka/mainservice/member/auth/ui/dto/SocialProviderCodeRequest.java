package kkakka.mainservice.member.auth.ui.dto;

import kkakka.mainservice.member.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialProviderCodeRequest {

    private String code;
    private MemberProviderName memberProviderName;

    public SocialProviderCodeDto toDto() {
        return SocialProviderCodeDto.create(code, memberProviderName);
    }

    public static SocialProviderCodeRequest create(String code, MemberProviderName memberProviderName) {
        return new SocialProviderCodeRequest(code, memberProviderName);
    }
}
