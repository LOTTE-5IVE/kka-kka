package kkakka.mainservice.product.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.product.SearchDto;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.SearchWords;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
