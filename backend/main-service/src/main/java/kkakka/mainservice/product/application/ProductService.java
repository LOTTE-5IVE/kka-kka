package kkakka.mainservice.product.application;

import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponseDto getProductDetail(Long productId) {

        Product productDetail = productRepository.findById(productId).orElseThrow(KkaKkaException::new);
        return modelMapper.map(productDetail, ProductResponseDto.class);
    }

    public ResponsePageDto getProductByRand() {

        Long qty = productRepository.countBy();
        int idx = (int) ((Math.random() * qty) / 10);
        Page<Product> randomProducts = productRepository.findAll(PageRequest.of(idx, 10));

        return ResponsePageDto.from(
                randomProducts,
                randomProducts.getContent().stream()
                        .map(ResponseCategoryProducts::from)
                        .collect(Collectors.toList())
        );
    }
}
