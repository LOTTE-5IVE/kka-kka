package kkakka.mainservice.member.domain;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 이름 수정 - 성공")
    @Test
    void updateMemberName_success() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updateName("수정된이름");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getName()).isEqualTo(member.getName());
    }

    @DisplayName("회원 이름 수정 - 실패(잘못된 형식의 이름)")
    @Test
    void updateMemberName_fail() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updateName("wrong-name");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getName()).isEqualTo(MEMBER_01.getName());
    }

    @DisplayName("회원 이메일 수정 - 성공")
    @Test
    void updateMemberEmail_success() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updateEmail("email@test.com");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getEmail()).isNotEqualTo(MEMBER_01.getEmail());
        assertThat(updatedMember.getEmail()).isEqualTo(member.getEmail());
    }

    @DisplayName("회원 이메일 수정 - 실패(잘못된 형식의 이메일 주소)")
    @Test
    void updateMemberEmail_fail() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updateEmail("wrong-email");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getEmail()).isEqualTo(MEMBER_01.getEmail());
    }

    @DisplayName("회원 연락처 수정 - 성공")
    @Test
    void updateMemberPhone_success() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updatePhone("010-0000-0001");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getPhone()).isNotEqualTo(MEMBER_01.getPhone());
        assertThat(updatedMember.getPhone()).isEqualTo(member.getPhone());
    }

    @DisplayName("회원 연락처 수정 - 실패(잘못된 형식의 연락처)")
    @Test
    void updateMemberPhone_fail() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getUserProfile().getId(),
                                MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );

        // when
        member.updatePhone("010-0000-0001123");
        memberRepository.save(member);

        // then
        final Member updatedMember = memberRepository.findById(member.getId()).get();
        assertThat(updatedMember.getPhone()).isEqualTo(MEMBER_01.getPhone());
    }
}
