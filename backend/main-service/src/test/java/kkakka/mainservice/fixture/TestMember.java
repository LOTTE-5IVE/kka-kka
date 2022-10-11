package kkakka.mainservice.fixture;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.member.auth.ui.Authority;

public enum TestMember {

    MEMBER_00("0000", "1", "0000", "member00",
            "default@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "00~00", Authority.MEMBER),
    MEMBER_01("0001", "1", "0001", "member01",
            "test01@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "20~29", Authority.MEMBER),
    MEMBER_02("0002", "2", "0002", "member02",
            "test01@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "20~29", Authority.MEMBER),
    MEMBER_03("0003", "3", "0003", "member03",
            "test01@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "20~29", Authority.MEMBER),
    MEMBER_04("0004", "4", "0004", "member04",
            "test01@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "20~29", Authority.MEMBER),
    MEMBER_05("0005", "5", "0005", "member05",
            "test01@email.com", "010-0000-0000", "대한민국 서울특별시 비트교육센터", "20~29", Authority.MEMBER);

    private String code;
    private String accessToken;
    private String providerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String ageGroup;
    private Authority authority;
    private UserProfile userProfile;

    TestMember(String code, String accessToken, String providerId, String name, String email,
            String phone, String address, String ageGroup, Authority authority) {
        this.code = code;
        this.accessToken = accessToken;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.ageGroup = ageGroup;
        this.authority = authority;
        this.userProfile = new TestUserProfile(
                UUID.randomUUID().toString(),
                name,
                email,
                ageGroup,
                phone
        );
    }

    private static final List<TestMember> FIXTURES =
            Arrays.stream(TestMember.values()).collect(toList());

    public static TestMember findByCode(String code) {
        return FIXTURES.stream()
                .filter((member) -> code.equals(member.code))
                .findAny()
                .orElse(MEMBER_00);
    }

    public static boolean isValidToken(String accessToken) {

        return FIXTURES.stream()
                .anyMatch((member) -> member.accessToken.equals(accessToken));
    }

    public String getCode() {
        return code;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAgeGroup() {
        return ageGroup;
    }
}
