package kkakka.mainservice.fixture;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_00;

import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;

    public static Member MEMBER;
    public static Category CATEGORY_1;
    public static Category CATEGORY_2;
    public static Product PRODUCT_1;
    public static Product PRODUCT_2;
    public static Product PRODUCT_3;
    public static Product PRODUCT_4;
    public static Product PRODUCT_5;
    public static Order ORDER;
    public static ProductOrder PRODUCT_ORDER_1;
    public static ProductOrder PRODUCT_ORDER_2;
    public static ProductOrder PRODUCT_ORDER_3;
    public static ProductOrder PRODUCT_ORDER_4;
    public static ProductOrder PRODUCT_ORDER_5;

    @Override
    public void run(String... args) {
        MEMBER = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_00.getProviderId(), MemberProviderName.TEST),
                        TEST_MEMBER_00.getName(),
                        TEST_MEMBER_00.getEmail(),
                        TEST_MEMBER_00.getPhone(),
                        TEST_MEMBER_00.getAgeGroup()
                )
        );

        CATEGORY_1 = categoryRepository.save(new Category("test-category-01"));
        CATEGORY_2 = categoryRepository.save(new Category("test-category-02"));

        PRODUCT_1 = productRepository.save(new Product(CATEGORY_1, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL"));
        PRODUCT_2 = productRepository.save(new Product(CATEGORY_1, "롯데 마가렛트 오리지널 176g", 4480, 10,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL"));
        PRODUCT_3 = productRepository.save(new Product(CATEGORY_1, "롯데 웨하스 바닐라맛 50g", 4480, 10,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL"));
        PRODUCT_4 = productRepository.save(new Product(CATEGORY_2, "롯데 웨하스 딸기맛 50g", 4480, 10,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL"));
        PRODUCT_5 = productRepository.save(new Product(CATEGORY_2, "롯데 롯샌 파인애플 105g", 4480, 10,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL"));

        PRODUCT_ORDER_1 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), 1)
        );
        PRODUCT_ORDER_2 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), 1)
        );
        PRODUCT_ORDER_3 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), 1)
        );
        PRODUCT_ORDER_4 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), 1)
        );
        PRODUCT_ORDER_5 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), 1)
        );

        final List<ProductOrder> productOrders = List.of(PRODUCT_ORDER_1, PRODUCT_ORDER_2,
                PRODUCT_ORDER_3, PRODUCT_ORDER_4, PRODUCT_ORDER_5);
        ORDER = orderRepository.save(
                Order.create(MEMBER, productOrders.stream().mapToInt(ProductOrder::getPrice).sum(),
                        productOrders)
        );
    }
}
