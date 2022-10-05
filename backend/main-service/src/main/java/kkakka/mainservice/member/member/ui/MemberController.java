package kkakka.mainservice.member.member.ui;

import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Member Service";
    }

    @PostMapping("/me")
    public ResponseEntity<Long> showMemberInfo(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }
}
