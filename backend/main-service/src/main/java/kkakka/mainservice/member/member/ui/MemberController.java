package kkakka.mainservice.member.member.ui;

import javax.annotation.PostConstruct;
import kkakka.mainservice.member.auth.exception.AuthorizationException;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.application.dto.MemberUpdateDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.member.member.ui.dto.MemberInfoRequest;
import kkakka.mainservice.member.member.ui.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service";
    }

    @PostConstruct
    public void init() {
        memberRepository.save(
                Member.create(
                        Provider.create("test", MemberProviderName.TEST),
                        "신우주",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMemberInfo(@AuthenticationPrincipal LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            throw new AuthorizationException();
        }

        Member member = memberService.showInfo(loginMember.getId());
        return ResponseEntity.ok(member.toDto());
    }

    @PatchMapping("/me")
    public ResponseEntity<Long> editMemberInfo(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody MemberInfoRequest memberInfoRequest
    ) {
        if (loginMember.isAnonymous()) {
            throw new AuthorizationException();
        }

        memberService.updateMember(MemberUpdateDto.create(loginMember.getId(), memberInfoRequest));
        return ResponseEntity.ok().build();
    }
}
