package kkakka.mainservice.auth.infrastructure.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.UserProfileDto;
import kkakka.mainservice.auth.domain.Provider;
import kkakka.mainservice.auth.domain.ProviderName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserProfile implements UserProfile {

    private ProviderName providerName;
    private String id;
    private String name;
    private String email;
    private String ageGroup;
    private String phone;

    @Override
    public UserProfileDto toDto() {
        return new UserProfileDto(
                Provider.create(id, providerName()),
                name, email, ageGroup, phone
        );
    }

    @Override
    public ProviderName providerName() {
        return ProviderName.GOOGLE;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getAgeGroup() {
        return ageGroup;
    }

    @Override
    public String getPhone() {
        return phone;
    }
}
