package kkakka.mainservice.member.ui;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;
    @GetMapping("/health_check")
    public String status(){
        return "It's Working in Member Service";
    }

    @PostConstruct
    public void init(){
        memberRepository.save(new Member(1L,"신우주"));
    }
}
