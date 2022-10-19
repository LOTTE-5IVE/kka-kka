package kkakka.mainservice.category.domain.repository;

import kkakka.mainservice.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
