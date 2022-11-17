package kkakka.mainservice.fixture;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_00;

import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.ProviderName;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.RecipientDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.Recipient;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.NutritionRepository;
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
    @Autowired
    private NutritionRepository nutritionRepository;

    public static Member MEMBER;
    public static Category CATEGORY_1;
    public static Category CATEGORY_2;
    public static Nutrition NUTRITION_1;
    public static Nutrition NUTRITION_2;
    public static Nutrition NUTRITION_3;
    public static Nutrition NUTRITION_4;
    public static Nutrition NUTRITION_5;
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
    public static List<Product> ALL_PRODUCTS;

    @Override
    public void run(String... args) {
        MEMBER = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_00.getProviderId(), ProviderName.TEST),
                        TEST_MEMBER_00.getName(),
                        TEST_MEMBER_00.getEmail(),
                        TEST_MEMBER_00.getPhone(),
                        TEST_MEMBER_00.getAgeGroup()
                )
        );

        CATEGORY_1 = categoryRepository.save(new Category("test-category-01"));
        CATEGORY_2 = categoryRepository.save(new Category("test-category-02"));

        NUTRITION_1 = nutritionRepository.save(
                new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370", "0")
        );
        NUTRITION_2 = nutritionRepository.save(
                new Nutrition("112", "13", "6", "1.5", "6", "3.3", "0.2", "15", "80", "0")
        );
        NUTRITION_3 = nutritionRepository.save(
                new Nutrition("285", "31", "15", "2", "17", "12", "0", "1.5", "45", "0")
        );
        NUTRITION_4 = nutritionRepository.save(
                new Nutrition("285", "33", "15", "2", "16", "11", "0", "1.5", "4.5", "0")
        );
        NUTRITION_5 = nutritionRepository.save(
                new Nutrition("270", "36", "15", "2.2", "13", "7", "0.5", "0", "150", "0")
        );

        PRODUCT_1 = productRepository.save(new Product(CATEGORY_1, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 100,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL", NUTRITION_1));
        PRODUCT_2 = productRepository.save(new Product(CATEGORY_1, "롯데 마가렛트 오리지널 176g", 4480, 100,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL", NUTRITION_2));
        PRODUCT_3 = productRepository.save(new Product(CATEGORY_1, "롯데 웨하스 바닐라맛 50g", 4480, 100,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL", NUTRITION_3));
        PRODUCT_4 = productRepository.save(new Product(CATEGORY_2, "롯데 웨하스 딸기맛 50g", 4480, 100,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL", NUTRITION_4));
        PRODUCT_5 = productRepository.save(new Product(CATEGORY_2, "롯데 롯샌 파인애플 105g", 4480, 100,
                "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png",
                "상세URL", NUTRITION_5));

        ALL_PRODUCTS = List.of(PRODUCT_1, PRODUCT_2, PRODUCT_3, PRODUCT_4, PRODUCT_5);

        PRODUCT_ORDER_1 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), PRODUCT_1.getDiscount(), 1)
        );
        PRODUCT_ORDER_2 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), PRODUCT_1.getDiscount(), 1)
        );
        PRODUCT_ORDER_3 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), PRODUCT_1.getDiscount(), 1)
        );
        PRODUCT_ORDER_4 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), PRODUCT_1.getDiscount(), 1)
        );
        PRODUCT_ORDER_5 = productOrderRepository.save(
                ProductOrder.create(PRODUCT_1, PRODUCT_1.getPrice(), PRODUCT_1.getDiscount(), 1)
        );

        final List<ProductOrder> productOrders = List.of(PRODUCT_ORDER_1, PRODUCT_ORDER_2,
                PRODUCT_ORDER_3, PRODUCT_ORDER_4, PRODUCT_ORDER_5);
        ORDER = orderRepository.save(
                Order.create(MEMBER,
                        Recipient.from(RecipientDto.create(MEMBER.getName(), MEMBER.getEmail(),
                                MEMBER.getPhone(), MEMBER.getAddress())),
                        productOrders.stream().mapToInt(ProductOrder::getPrice).sum(),
                        productOrders)
        );
    }
}
