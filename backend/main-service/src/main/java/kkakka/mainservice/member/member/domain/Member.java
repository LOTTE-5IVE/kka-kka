package kkakka.mainservice.member.member.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kkakka.mainservice.member.member.ui.dto.MemberResponse;
import kkakka.mainservice.member.member.util.MemberInfoPatterns;
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

    @Embedded
    private Provider provider;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String ageGroup;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public static Member create(Long id, Provider provider, String name, String email, String phone,
        String address, String ageGroup, Grade grade) {
        return new Member(id, provider, name, email, phone, address, ageGroup, grade);
    }

    public static Member create(Provider provider, String name, String email, String phone,
        String ageGroup) {
        return new Member(null, provider, name, email, phone, "", ageGroup, Grade.BRONZE);
    }

    public void updateName(String name) {
        if (MemberInfoPatterns.isValidName(name)) {
            this.name = name;
        }
    }

    public void updateEmail(String email) {
        if (MemberInfoPatterns.isValidEmail(email)) {
            this.email = email;
        }
    }

    public void updatePhone(String phone) {
        if (MemberInfoPatterns.isValidPhoneNumber(phone)) {
            this.phone = phone;
        }
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void gradeUp(Grade grade) {
        this.grade = grade;
    }

    public MemberResponse toDto() {
        return new MemberResponse(name, email, phone, address, ageGroup, grade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
