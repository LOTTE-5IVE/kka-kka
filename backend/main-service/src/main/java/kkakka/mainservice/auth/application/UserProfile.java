package kkakka.mainservice.auth.application;

import kkakka.mainservice.auth.domain.ProviderName;

public interface UserProfile {

    ProviderName providerName();

    String getId();

    String getName();

    String getEmail();

    String getAgeGroup();

    String getPhone();
}
