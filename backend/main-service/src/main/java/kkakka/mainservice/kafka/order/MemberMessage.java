package kkakka.mainservice.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.member.member.domain.Grade;
import kkakka.mainservice.member.member.domain.ProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MemberMessage {

    private Long id;
    private String provider;
    private String name;
    private String email;
    private String phone;
    private String address;
    @JsonProperty("age_group")
    private String ageGroup;
    private String grade;

    public static MemberMessage create(Long id, ProviderName providerName, String name, String email,
            String phone, String address, String ageGroup, Grade grade) {
        return new MemberMessage(id, providerName.name(), name, email, phone, address,
                ageGroup, grade.name());
    }
}
