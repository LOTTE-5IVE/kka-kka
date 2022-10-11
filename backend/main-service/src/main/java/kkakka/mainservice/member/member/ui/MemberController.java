package kkakka.mainservice.member.member.ui;

import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.auth.ui.MemberOnly;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.application.dto.MemberUpdateDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.ui.dto.MemberInfoRequest;
import kkakka.mainservice.member.member.ui.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@MemberOnly
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service";
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMemberInfo(
            @AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.showInfo(loginMember.getId());
        return ResponseEntity.ok(member.toDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> editMemberInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        memberService.updateMember(MemberUpdateDto.create(loginMember.getId(), memberInfoRequest));
        return ResponseEntity.ok().build();
    }
}
