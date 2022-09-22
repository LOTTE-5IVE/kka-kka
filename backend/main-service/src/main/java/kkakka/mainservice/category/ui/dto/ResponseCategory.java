package kkakka.mainservice.category.ui.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseCategory {
    private Long productId;
    private String name;
    private int price;
    private String image_url;
    private int discount;
    private Date registered_at;
}
