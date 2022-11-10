package kkakka.mainservice.elasticsearch.ui;

import kkakka.mainservice.elasticsearch.application.ProductDocumentService;
import kkakka.mainservice.elasticsearch.ui.dto.SearchParamRequest;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/es")
@RestController
public class ProductDocumentController {

    private final ProductDocumentService productDocumentService;

    @GetMapping("/search")
    public ResponseEntity<SearchResultResponse> showProductsBySearch(
        @ModelAttribute SearchParamRequest searchParamRequest,
        @PageableDefault(size = 9) Pageable pageable) {
        SearchResultResponse result = productDocumentService.findByKeyword(
            searchParamRequest.toDto(),
            pageable
        );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
