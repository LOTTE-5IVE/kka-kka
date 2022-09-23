package kkakka.mainservice.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import kkakka.mainservice.auth.infrastructure.NaverUserProfile;
import kkakka.mainservice.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @DisplayName("회원 생성 테스트 - 성공")
    @Test
    void createMemberSuccess() {
        // given
        final NaverUserProfile 네이버회원01 = new NaverUserProfile("1234", "네이버회원01", "test@email.com",
                "20~29", "010-0000-0000");

        // when
        final Member member = memberService.createMember(네이버회원01);

        // then
        assertThat(member.getId()).isNotNull();
    }
}