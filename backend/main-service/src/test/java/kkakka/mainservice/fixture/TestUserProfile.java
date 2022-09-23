package kkakka.mainservice.fixture;

import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.domain.ProviderName;

public class TestUserProfile implements UserProfile {

    private String id;
    private String name;
    private String email;
    private String ageGroup;
    private String phone;

    public TestUserProfile(String id, String name, String email, String ageGroup, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ageGroup = ageGroup;
        this.phone = phone;
    }

    @Override
    public ProviderName providerName() {
        return ProviderName.TEST;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getAgeGroup() {
        return this.ageGroup;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }
}
