package kkakka.mainservice.product.application;

import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponseDto getProductDetail(Long productId) {

        Product productDetail = productRepository.findById(productId).orElseThrow(KkaKkaException::new);
        return modelMapper.map(productDetail, ProductResponseDto.class);
    }
}
