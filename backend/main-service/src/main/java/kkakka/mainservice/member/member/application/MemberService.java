package kkakka.mainservice.member.member.application;

import kkakka.mainservice.member.auth.application.dto.UserProfile;
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
}
