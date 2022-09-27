package kkakka.mainservice.cart;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    public static Member MEMBER;

    @Override
    public void run(String... args) throws Exception {
        MEMBER = memberRepository.save(new Member(1L, "신우주"));
        Category category = new Category(1L, "비스킷/샌드");
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
    }

}
