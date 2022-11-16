package kkakka.mainservice.elasticsearch.domain;

import kkakka.mainservice.elasticsearch.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indices.PRODUCT_INDEX)
public class ProductDocument {

    private Long id;

    private Integer categoryid;
    private String category;
    private String name;
    private Integer price;
    private String calorie;
}
