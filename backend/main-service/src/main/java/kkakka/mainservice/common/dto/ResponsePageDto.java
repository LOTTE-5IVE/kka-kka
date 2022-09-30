package kkakka.mainservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

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

    private List<?> itemList;

    public static ResponsePageDto from(Page<?> page, List<?> itemList) {
        return new ResponsePageDto(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                Optional.of(page.getTotalElements()).orElse(0L).intValue(),
                page.hasNext(),
                page.isFirst(),
                page.isLast(),
                itemList
        );
    }
}
