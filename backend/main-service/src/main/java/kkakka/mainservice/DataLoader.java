package kkakka.mainservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    private static final Map<String, Category> categories = new HashMap<>();

    @Transactional
    public void saveData(List<String[]> data) {
        saveCategory();

        for (String[] datum : data) {
            saveProduct(datum);
        }

        saveUser();
    }

    private void saveUser() {
        memberRepository.save(
                Member.create(
                        Provider.create("test", MemberProviderName.TEST),
                        "신우주",
                        "test@email.com",
                        "010-000-0000",
                        "20~29"
                )
        );
    }

    public void saveProduct(String[] productRow) {
        final String category = productRow[0];
        final String name = productRow[1];
        final String price = productRow[2];
        final String defaultImageUrl = productRow[3];
        final String detailImageUrl = productRow[4];
        productRepository.save(
                new Product(
                        categories.get(category),
                        name,
                        Integer.parseInt(price),
                        1000,
                        defaultImageUrl,
                        detailImageUrl
                )
        );
    }

    public void saveCategory() {
        Category category1 = categoryRepository.save(new Category("비스킷/샌드"));
        Category category2 = categoryRepository.save(new Category("스낵/봉지과자"));
        Category category3 = categoryRepository.save(new Category("박스과자"));
        Category category4 = categoryRepository.save(new Category("캔디/사탕/젤리"));
        Category category5 = categoryRepository.save(new Category("시리얼/바"));
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
