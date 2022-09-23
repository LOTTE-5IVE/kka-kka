package kkakka.mainservice.category.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ResponseCategoryProducts {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Integer discount;
    private Date registeredAt;
}
