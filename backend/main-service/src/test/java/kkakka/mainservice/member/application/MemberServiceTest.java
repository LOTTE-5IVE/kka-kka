package kkakka.mainservice.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import kkakka.mainservice.auth.infrastructure.naver.NaverUserProfile;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.application.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @DisplayName("회원 생성 - 성공")
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

    @DisplayName("회원 조회 혹은 생성 - 성공")
    @Test
    void findOrCreateMemberSuccess() {
        // given
        final NaverUserProfile 네이버회원01 = new NaverUserProfile("1234", "네이버회원01", "test@email.com",
                "20~29", "010-0000-0000");

        // when
        final Member createdMember = memberService.findOrCreateMember(네이버회원01);
        final Member findMember = memberService.findOrCreateMember(네이버회원01);

        // then
        assertThat(createdMember.getProvider().equals(findMember.getProvider())).isTrue();
        assertThat(createdMember.equals(findMember)).isTrue();
    }
}
