package kkakka.mainservice.member.member.ui.dto;

import kkakka.mainservice.member.member.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String ageGroup;
    private Grade grade;
}
