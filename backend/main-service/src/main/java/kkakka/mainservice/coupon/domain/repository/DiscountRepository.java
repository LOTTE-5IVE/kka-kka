package kkakka.mainservice.coupon.domain.repository;

import kkakka.mainservice.coupon.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
