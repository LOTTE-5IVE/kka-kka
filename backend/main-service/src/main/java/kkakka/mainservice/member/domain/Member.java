package kkakka.mainservice.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    public static Member create(Long id, Provider provider, String name, String email, String phone,
            String address, String ageGroup, Grade grade) {
        return new Member(id, provider, name, email, phone, address, ageGroup, grade);
    }
}
