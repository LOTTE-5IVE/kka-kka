package kkakka.authservice.auth.infrastructure.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.authservice.auth.application.UserProfile;
import kkakka.authservice.auth.application.dto.UserProfileDto;
import kkakka.authservice.auth.domain.Provider;
import kkakka.authservice.auth.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserProfile implements UserProfile {

    private static final String DEFAULT_PHONE_NUMBER = "010-0000-0000";
    private String id;
    @JsonProperty(value = "properties")
    private KakaoProperty kakaoProperty;
    @JsonProperty(value = "kakao_account")
    private KakaoAccount kakaoAccount;

    @Override
    public UserProfileDto toDto() {
        return new UserProfileDto(
                Provider.create(id, providerName()),
                kakaoProperty.name,
                kakaoAccount.email,
                kakaoAccount.ageGroup,
                DEFAULT_PHONE_NUMBER
        );
    }

    @Override
    public ProviderName providerName() {
        return ProviderName.KAKAO;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return kakaoProperty.name;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public String getAgeGroup() {
        return kakaoAccount.ageGroup;
    }

    @Override
    public String getPhone() {
        return DEFAULT_PHONE_NUMBER;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoAccount {

        private String email;
        @JsonProperty(value = "age_range")
        private String ageGroup;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class KakaoProperty {

        @JsonProperty(value = "nickname")
        private String name;
    }
}
