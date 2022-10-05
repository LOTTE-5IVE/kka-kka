package kkakka.mainservice.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.member.application.MemberService;
import kkakka.mainservice.member.member.application.dto.MemberUpdateDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.member.member.ui.dto.MemberInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원 생성 - 성공")
    @Test
    void createMember_success() {
        // given
        final UserProfile 네이버회원01 = new UserProfile(
                Provider.create("0001", MemberProviderName.NAVER), "네이버회원01", "test@email.com",
                "20~29", "010-0000-0000");

        // when
        final Member member = memberService.createMember(네이버회원01);

        // then
        assertThat(member.getId()).isNotNull();
    }

    @DisplayName("회원 조회 혹은 생성 - 성공")
    @Test
    void findOrCreateMember_success() {
        // given
        final UserProfile 네이버회원01 = new UserProfile(
                Provider.create("0002", MemberProviderName.NAVER), "네이버회원01", "test@email.com",
                "20~29", "010-0000-0000");

        // when
        final Member createdMember = memberService.findOrCreateMember(네이버회원01);
        final Member findMember = memberService.findOrCreateMember(네이버회원01);

        // then
        assertThat(createdMember.getProvider().equals(findMember.getProvider())).isTrue();
        assertThat(createdMember.equals(findMember)).isTrue();
    }

    @DisplayName("회원 정보 수정 - 성공")
    @Test
    void updateMemberInfo_success() {
        // given
        final Member member = memberService.findOrCreateMember(
                new UserProfile(Provider.create("1234", MemberProviderName.TEST), "테스트회원",
                        "test@email.com",
                        "20~29", "010-0000-0000")
        );

        // when
        final MemberUpdateDto memberUpdateDto = MemberUpdateDto.create(member.getId(),
                new MemberInfoRequest("새로운이름", null, null, null)
        );
        memberService.updateMember(memberUpdateDto);

        // then
        final String updatedName = memberRepository.findById(member.getId()).get()
                .getName();
        assertThat(updatedName).isEqualTo("새로운이름");
    }
}
