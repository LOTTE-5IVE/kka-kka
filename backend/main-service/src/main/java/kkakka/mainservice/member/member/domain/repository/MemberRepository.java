package kkakka.mainservice.member.member.domain.repository;

import java.util.Optional;
import kkakka.mainservice.member.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.provider.providerId = :providerId")
    Optional<Member> findByProviderId(@Param("providerId") String providerId);
}
