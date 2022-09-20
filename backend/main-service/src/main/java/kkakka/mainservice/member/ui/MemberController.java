package kkakka.mainservice.member.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/health_check")
    public String status(){
        return "It's Working in Member Service";
    }
}
