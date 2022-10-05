package kkakka.mainservice.member.member.application;

import java.util.Optional;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.member.auth.application.dto.UserProfile;
import kkakka.mainservice.member.member.application.dto.MemberUpdateDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member findOrCreateMember(UserProfile userProfile) {
        return memberRepository
                .findByProviderId(userProfile.getProviderId())
                .orElseGet(() -> createMember(userProfile));
    }

    public Member createMember(UserProfile userProfile) {
        return memberRepository.save(
                Member.create(
                        userProfile.getProvider(),
                        userProfile.getName(),
                        userProfile.getEmail(),
                        userProfile.getPhone(),
                        userProfile.getAgeGroup()
                )
        );
    }

    @Transactional
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        final Member member = memberRepository.findById(memberUpdateDto.getId())
                .orElseThrow(NotFoundMemberException::new);

        if (Optional.ofNullable(memberUpdateDto.getName()).isPresent()) {
            member.updateName(memberUpdateDto.getName());
        }

        if (Optional.ofNullable(memberUpdateDto.getEmail()).isPresent()) {
            member.updateEmail(memberUpdateDto.getEmail());
        }

        if (Optional.ofNullable(memberUpdateDto.getPhone()).isPresent()) {
            member.updatePhone(memberUpdateDto.getPhone());
        }

        if (Optional.ofNullable(memberUpdateDto.getAddress()).isPresent()) {
            member.updateAddress(memberUpdateDto.getAddress());
        }

        memberRepository.save(member);
    }
}
