package kkakka.mainservice.category.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseCategoryProducts {
    private Long id;
    private String name;
    private Integer price;
    private String image_url;
    private Integer discount;
    private Date registered_at;
}
