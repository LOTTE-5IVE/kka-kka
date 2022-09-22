package kkakka.mainservice.product.ui;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @PostConstruct
    public void init() {
        Category category = new Category(1L,"비스킷/샌드");
        productRepository.save(new Product(category,"롯데 제로 초콜릿칩 쿠키 168g",4480,10,"https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png","상세URL"));
        productRepository.save(new Product(category,"롯데 제로 초콜릿칩 쿠키 168g",4480,10,"https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png","상세URL"));
        productRepository.save(new Product(category,"롯데 제로 초콜릿칩 쿠키 168g",4480,10,"https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png","상세URL"));
        productRepository.save(new Product(category,"롯데 제로 초콜릿칩 쿠키 168g",4480,10,"https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png","상세URL"));
        productRepository.save(new Product(category,"롯데 제로 초콜릿칩 쿠키 168g",4480,10,"https://user-images.githubusercontent.com/99088509/191633507-6280963f-6363-4137-ac2a-a8a060d28669.png","상세URL"));
    }
}
