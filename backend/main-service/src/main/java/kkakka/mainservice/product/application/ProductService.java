package kkakka.mainservice.product.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.common.config.MapperConfig;
import kkakka.mainservice.product.SearchDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.SearchWords;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import org.modelmapper.ModelMapper;
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
        ProductResponseDto result = mapper.map(productDetail.get(),
            ProductResponseDto.class); // NPE 처리 필요
        result.setCategoryName(productDetail.get().getCategory().getName());

        return result;
    }

    public List<ProductResponseDto> showProductsBySearch(SearchDto searchDto) {

        String keyword = searchDto.getKeyword();
        SearchWords searchWords = SearchWords.create(keyword);
        List<String> getSearchWords = searchWords.getSearchWords();
        List<Product> products = new ArrayList<>();

        for (String searchWord : getSearchWords) {
            products.addAll(productRepository.findByCategory(searchWord));
        }
        for (String searchWord : getSearchWords) {
            products.addAll(productRepository.findByName(searchWord));
        }

        List<ProductResponseDto> productResponseDtos = products.stream()
            .distinct()
            .map(ProductResponseDto::create)
            .collect(Collectors.toList());

        return productResponseDtos;
    }
}
