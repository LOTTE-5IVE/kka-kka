package kkakka.mainservice.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import kkakka.mainservice.auth.application.UserProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Embedded
//    private Provider provider;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String ageGroup;

//    @Enumerated(EnumType.STRING)
//    private Grade grade;

//    public static Member create(Long id, Provider provider, String name, String email, String phone,
//                                String address, String ageGroup, Grade grade) {
//        return new Member(id, provider, name, email, phone, address, ageGroup, grade);
//    }
//
//    public static Member create(UserProfile userProfile, Provider provider) {
//        return new Member(null, provider, userProfile.getName(),
//                userProfile.getEmail(), userProfile.getPhone(), "",
//                userProfile.getAgeGroup(), Grade.BRONZE);
//    }

    /* 테스트용 생성자 */
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
