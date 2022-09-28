package kkakka.mainservice.member.auth.application.dto;

import kkakka.mainservice.member.member.domain.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Provider provider;
    private String name;
    private String email;
    private String ageGroup;
    private String phone;
}