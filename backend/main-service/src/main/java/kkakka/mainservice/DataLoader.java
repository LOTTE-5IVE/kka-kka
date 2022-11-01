package kkakka.mainservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductOrderRepository productOrderRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final NutritionRepository nutritionRepository;

    public static final Map<String, Category> categories = new LinkedHashMap<>();
    private static Product testProduct;
    private static Member testMember;

    @Transactional
    public void saveData(List<String[]> data) {
        saveCategory();

        List<Product> products = new ArrayList<>();
        for (String[] datum : data) {
            products.add(saveProduct(datum));
        }
        testProduct = products.get(0);

        saveUser();
        saveOrderAndReview();
    }

    private void saveOrderAndReview() {
        final List<ProductOrder> productOrders = productOrderRepository.saveAll(
                Arrays.asList(
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1),
                        ProductOrder.create(testProduct, testProduct.getPrice(), 1)
                ));

        Recipient testRecipient = Recipient.from(RecipientDto.create(
                testMember.getName(), testMember.getEmail(), testMember.getPhone(),
                testMember.getAddress()
        ));
        orderRepository.save(
                Order.create(testMember, testRecipient, testProduct.getPrice(), productOrders));
        reviewRepository.save(Review.create("test-review1", 5.0, testMember, productOrders.get(0)));
        reviewRepository.save(Review.create("test-review2", 3.5, testMember, productOrders.get(1)));
        reviewRepository.save(Review.create("test-review3", 4.5, testMember, productOrders.get(2)));
        reviewRepository.save(Review.create("test-review4", 4.0, testMember, productOrders.get(3)));
        reviewRepository.save(Review.create("test-review5", 5.0, testMember, productOrders.get(4)));
        reviewRepository.save(Review.create("test-review6", 2.5, testMember, productOrders.get(5)));
        reviewRepository.save(Review.create("test-review7", 3.5, testMember, productOrders.get(6)));
    }

    private void saveUser() {
        testMember = memberRepository.save(
                Member.create(
                        Provider.create("0001", ProviderName.TEST),
                        "신우주",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
        memberRepository.save(
                Member.create(
                        Provider.create("0002", ProviderName.TEST),
                        "서지훈",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
        memberRepository.save(
                Member.create(
                        Provider.create("0003", ProviderName.TEST),
                        "김혜연",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
        memberRepository.save(
                Member.create(
                        Provider.create("0004", ProviderName.TEST),
                        "오명주",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
        memberRepository.save(
                Member.create(
                        Provider.create("0005", ProviderName.TEST),
                        "최솔지",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
    }

    public Product saveProduct(String[] productRow) {
        final String category = productRow[0];
        final String name = productRow[1];
        final String price = productRow[2];
        final String defaultImageUrl = productRow[3];
        final String detailImageUrl = productRow[4];
        final String calorie = productRow[5];
        final String carbohydrate = productRow[6];
        final String sugar = productRow[7];
        final String protein = productRow[8];
        final String fat = productRow[9];
        final String saturatedFat = productRow[10];
        final String transFat = productRow[11];
        final String cholesterol = productRow[12];
        final String sodium = productRow[13];
        final String calcium = productRow[14];

        final Nutrition nutrition = nutritionRepository.save(
                new Nutrition(
                        calorie, carbohydrate, sugar, protein,
                        fat, saturatedFat, transFat, cholesterol, sodium, calcium
                )
        );

        return productRepository.save(
                new Product(
                        categories.get(category),
                        name,
                        Integer.parseInt(price),
                        1000,
                        defaultImageUrl,
                        detailImageUrl,
                        nutrition
                )
        );
    }

    public void saveCategory() {
        Category category1 = categoryRepository.save(new Category("비스킷/샌드"));
        Category category2 = categoryRepository.save(new Category("스낵/봉지과자"));
        Category category3 = categoryRepository.save(new Category("박스과자"));
        Category category4 = categoryRepository.save(new Category("시리얼/바"));
        Category category5 = categoryRepository.save(new Category("캔디/사탕/젤리"));
        Category category6 = categoryRepository.save(new Category("초콜릿"));
        Category category7 = categoryRepository.save(new Category("껌/자일리톨"));
        Category category8 = categoryRepository.save(new Category("선물세트"));

        categories.put(category1.getName(), category1);
        categories.put(category2.getName(), category2);
        categories.put(category3.getName(), category3);
        categories.put(category4.getName(), category4);
        categories.put(category5.getName(), category5);
        categories.put(category6.getName(), category6);
        categories.put(category7.getName(), category7);
        categories.put(category8.getName(), category8);
    }
}
