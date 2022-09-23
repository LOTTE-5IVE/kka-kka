package kkakka.mainservice.product.application;

import kkakka.mainservice.common.config.MapperConfig;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto getProductDetail(Long productId) {

        Optional<Product> productDetail = productRepository.findById(productId);

        ModelMapper mapper = new MapperConfig().modelMapper();
        ProductResponseDto result = mapper.map(productDetail.get(), ProductResponseDto.class); // NPE 처리 필요
        result.setCategoryName(productDetail.get().getCategory().getName());

        return result;
    }
}
