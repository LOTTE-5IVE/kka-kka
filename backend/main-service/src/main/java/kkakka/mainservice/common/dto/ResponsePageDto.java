package kkakka.mainservice.common.dto;

import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ResponsePageDto {

    private Integer currentPage;
    private Integer size;
    private Integer numberOfItem;
    private Integer totalPages;
    private Integer totalItemCount;
    private boolean hasNext;
    private boolean first;
    private boolean last;

    private List<ResponseCategoryProducts> itemList;

    public static ResponsePageDto from(Page<Product> page) {
        return new ResponsePageDto(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                Long.valueOf(Optional.ofNullable(page.getTotalElements()).orElse(0L)).intValue(),
                page.hasNext(),
                page.isFirst(),
                page.isLast(),
                castTo(page.getContent())
        );
    }

    public static List<ResponseCategoryProducts> castTo(List<Product> productList) {
        return productList.stream().map(
                ResponseCategoryProducts::from
        ).collect(Collectors.toList());
    }

}
