package kkakka.mainservice.cart;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;
    public static Member MEMBER;

    @Override
    public void run(String... args) throws Exception {
        MEMBER = new Member(1L, "신우주");
        memberRepository.save(MEMBER);
    }

}
