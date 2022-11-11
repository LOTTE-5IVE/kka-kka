package kkakka.mainservice.elasticsearch.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import kkakka.mainservice.common.LocalDateTimeSerializer;
import kkakka.mainservice.elasticsearch.helper.Indices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indices.PRDUCT_INDEX)
public class ProductDocument {

    private Long id;

    private Integer categoryid;
    private String category;
    private String name;
    private Integer price;
    private Integer stock;
    private Integer discount;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registered_at;
    private String calorie;
}
