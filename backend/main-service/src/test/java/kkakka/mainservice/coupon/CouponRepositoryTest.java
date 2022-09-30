package kkakka.mainservice.coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CouponRepositoryTest {

  @Autowired
  private CouponRepository couponRepository;
  @Autowired
  private ProductRepository productRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @DisplayName("쿠폰 사용 테스트")
  @Test
  void useCoupon_success() {
    Product product = new Product(null, "testProduct", 1000, 10, "testUrl", "testUrl");
    productRepository.save(product);

    Coupon coupon = Coupon.create(null, product, "testCoupon", "1234", PriceRule.COUPON,
        LocalDateTime.now(), LocalDateTime.now(), 10, 500, 500);
    couponRepository.save(coupon);

    coupon.useCoupon();

    couponRepository.flush();
    assertThat(coupon.isUsed()).isTrue();
  }
}
