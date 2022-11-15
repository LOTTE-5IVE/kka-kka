package kkakka.mainservice.elasticsearch.ui.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkakka.mainservice.elasticsearch.application.ProductDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/es")
@RestController
public class ProductDocumentController {

    private final ProductDocumentService productDocumentService;

    @GetMapping("/auto")
    public ResponseEntity<Map<String, List<String>>> autoCompleteByKeyword(@RequestParam(value = "keyword", required = false) String keyword) {
        List<String> autoProductNames = productDocumentService.autoCompleteByKeyword(keyword);
        Map<String, List<String>> result = new HashMap<>();
        result.put("autoKeyword",autoProductNames);

        return ResponseEntity.ok().body(result);
    }
}
