package kkakka.mainservice.auth.infrastructure.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.UserProfileDto;
import kkakka.mainservice.auth.domain.Provider;
import kkakka.mainservice.auth.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserProfile implements UserProfile {

    @JsonProperty(value = "response")
    private NaverAccount naverAccount;

    public NaverUserProfile(String id, String name, String email, String ageGroup, String phone) {
        this.naverAccount = new NaverAccount(id, name, email, ageGroup, phone);
    }

    @Override
    public UserProfileDto toDto() {
        return new UserProfileDto(
                Provider.create(naverAccount.id, providerName()),
                naverAccount.name,
                naverAccount.email,
                naverAccount.ageGroup,
                naverAccount.phone
        );
    }

    @Override
    public ProviderName providerName() {
        return ProviderName.NAVER;
    }

    @Override
    public String getId() {
        return naverAccount.id;
    }

    @Override
    public String getName() {
        return naverAccount.name;
    }

    @Override
    public String getEmail() {
        return naverAccount.email;
    }

    @Override
    public String getAgeGroup() {
        return naverAccount.ageGroup;
    }

    @Override
    public String getPhone() {
        return naverAccount.phone;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NaverAccount {

        private String id;
        @JsonProperty(value = "nickname")
        private String name;
        private String email;
        @JsonProperty(value = "age")
        private String ageGroup;
        @JsonProperty(value = "mobile")
        private String phone;
    }
}
