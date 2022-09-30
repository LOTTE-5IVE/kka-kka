package kkakka.mainservice.product.application;

import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductResponseDto getProductDetail(Long productId) {

        Product productDetail = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(productDetail, ProductResponseDto.class);
    }
}
