package kkakka.mainservice.member.application;

import kkakka.mainservice.auth.infrastructure.NaverUserProfile;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.Provider;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(NaverUserProfile userProfile) {
        return memberRepository.save(
                Member.create(
                        userProfile,
                        Provider.create(userProfile.getId(), userProfile.providerName())
                )
        );
    }
}
