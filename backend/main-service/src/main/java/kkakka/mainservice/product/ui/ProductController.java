package kkakka.mainservice.product.ui;

import kkakka.mainservice.category.application.CategoryService;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.product.application.ProductService;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductRepository productRepository, ProductService productService
            , CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @PostConstruct
    public void init() {

        Category category = new Category(1L, "비스킷/샌드");
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
        productRepository.save(new Product(category, "롯데 제로 초콜릿칩 쿠키 168g", 4480, 10, "https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png", "상세URL"));
    }

    @GetMapping("/productView={productId}")
    public ResponseEntity<ProductResponseDto> showProductDetail(@PathVariable("productId") Long productId) {

        ProductResponseDto responseDto = productService.getProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ResponseCategoryProducts>> showCategoryProducts(@RequestParam("category") Long categoryId,
                                                                        Pageable pageable) {
        Page<ResponseCategoryProducts> result = categoryService.getProductsByCategoryId(categoryId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }
}
