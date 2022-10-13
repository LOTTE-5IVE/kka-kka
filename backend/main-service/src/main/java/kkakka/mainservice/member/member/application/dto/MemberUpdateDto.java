package kkakka.mainservice.member.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kkakka.mainservice.member.member.ui.dto.MemberInfoRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberUpdateDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public static MemberUpdateDto create(Long memberId, MemberInfoRequest memberInfoRequest) {
        return new MemberUpdateDto(memberId, memberInfoRequest.getName(),
                memberInfoRequest.getEmail(), memberInfoRequest.getPhone(),
                memberInfoRequest.getAddress());
    }
}
