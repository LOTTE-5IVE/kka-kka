package kkakka.mainservice.fixture;

import kkakka.mainservice.member.auth.application.dto.UserProfile;

public class TestUserProfile extends UserProfile {

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
