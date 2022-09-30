package kkakka.mainservice.cart;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class TestDataLoader implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public static Member MEMBER;
    public static Category CATEGORY;
    public static Product PRODUCT_1;
    public static Product PRODUCT_2;
    public static Product PRODUCT_3;
    public static Product PRODUCT_4;
    public static Product PRODUCT_5;

    @Override
    public void run(String... args) {
        MEMBER = new Member(1L, "신우주");
        memberRepository.save(MEMBER);

        Category category = new Category("test-category");
        CATEGORY = categoryRepository.save(category);
        PRODUCT_1 = productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        PRODUCT_2 = productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        PRODUCT_3 = productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        PRODUCT_4 = productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        PRODUCT_5 = productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
    }

}
