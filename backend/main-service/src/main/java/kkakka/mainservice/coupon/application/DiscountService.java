package kkakka.mainservice.coupon.application;

import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.coupon.domain.Discount;
import kkakka.mainservice.coupon.domain.DiscountType;
import kkakka.mainservice.coupon.domain.repository.DiscountRepository;
import kkakka.mainservice.coupon.ui.dto.DiscountRequestDto;
import kkakka.mainservice.coupon.ui.dto.DiscountResponseDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiscountService {

    final private DiscountRepository discountRepository;
    final private ProductRepository productRepository;
    final private CategoryRepository categoryRepository;

    /* 할인 등록 */
    @Transactional
    public Long createDiscount(DiscountRequestDto discountRequestDto) {
        if (discountRequestDto.isValidateDate() && discountRequestDto.isValidateDiscount()) {
            if (DiscountType.PRODUCT_DISCOUNT.equals(discountRequestDto.getDiscountType())) {
                Product product = getProduct(discountRequestDto);
                Discount discount = Discount.create(
                    product,
                    discountRequestDto.getName(),
                    discountRequestDto.getDiscount(),
                    discountRequestDto.getStartedAt(),
                    discountRequestDto.getExpiredAt());
                product.changeDiscount(discountRequestDto.getDiscount());
                productRepository.save(product);
                discountRepository.save(discount);
                return discount.getId();
            }

            if (DiscountType.CATEGORY_DISCOUNT.equals(discountRequestDto.getDiscountType())) {
                Category category = getCategory(discountRequestDto);
                Discount discount = Discount.create(
                    category,
                    discountRequestDto.getName(),
                    discountRequestDto.getDiscount(),
                    discountRequestDto.getStartedAt(),
                    discountRequestDto.getExpiredAt());
                List<Product> products = categoryRepository.findProductsByCategoryId(category.getId());
                for (Product product : products) {
                    product.changeDiscount(discountRequestDto.getDiscount());
                    productRepository.save(product);
                }
                discountRepository.save(discount);
                return discount.getId();
            }
        }
        throw new KkaKkaException();
    }

    private Product getProduct(DiscountRequestDto discountRequestDto) {
        return productRepository.findById(discountRequestDto.getProductId())
            .orElseThrow(IllegalArgumentException::new);
    }

    private Category getCategory(DiscountRequestDto discountRequestDto) {
        return categoryRepository.findById(discountRequestDto.getCategoryId())
            .orElseThrow(IllegalArgumentException::new);
    }

    /* 할인 삭제 */
    @Transactional
    public void deleteDiscount(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
            .orElseThrow(KkaKkaException::new);
        if (DiscountType.PRODUCT_DISCOUNT.equals(discount.getDiscountType())) {
            Product product = productRepository.findById(discount.getProductId())
                .orElseThrow(KkaKkaException::new);
            discount.deleteDiscount();
            product.deleteDiscount();
            discountRepository.save(discount);
            productRepository.save(product);
        }
        if (DiscountType.CATEGORY_DISCOUNT.equals(discount.getDiscountType())) {
            List<Product> products = categoryRepository.findProductsByCategoryId(
                discount.getCategoryId());
            for (Product product : products) {
                product.deleteDiscount();
                productRepository.save(product);
            }
            discount.deleteDiscount();
            discountRepository.save(discount);
        }
    }

    /* 할인 조회 */
    public List<DiscountResponseDto> showAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream().map(discount -> DiscountResponseDto.create(discount)).collect(
            Collectors.toList());
    }

}