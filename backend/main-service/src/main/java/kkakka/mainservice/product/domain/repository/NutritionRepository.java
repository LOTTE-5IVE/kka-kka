package kkakka.mainservice.product.domain.repository;

import kkakka.mainservice.product.domain.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {

}
