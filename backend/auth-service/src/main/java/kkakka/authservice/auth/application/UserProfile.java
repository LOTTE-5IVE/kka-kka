package kkakka.authservice.auth.application;

import kkakka.authservice.auth.application.dto.UserProfileDto;
import kkakka.authservice.auth.domain.ProviderName;

public interface UserProfile {

    UserProfileDto toDto();

    ProviderName providerName();

    String getId();

    String getName();

    String getEmail();

    String getAgeGroup();

    String getPhone();
}
