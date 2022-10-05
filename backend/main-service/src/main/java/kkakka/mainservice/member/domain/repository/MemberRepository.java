package kkakka.mainservice.member.domain.repository;

import java.util.List;
import kkakka.mainservice.member.domain.Grade;
import kkakka.mainservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query(value = "SELECT m FROM Member m WHERE m.grade = :grade")
  List<Member> findByGrade(@Param(value = "grade") Grade grade);
}
