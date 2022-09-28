package kkakka.mainservice.order.ui.dto;

import java.util.List;

public class OrderRequestDto {

    private Long memberId;
    private List<ProductOrderRequest> productOrderRequests;

    public Long getMemberId() {
        return memberId;
    }

    public List<ProductOrderRequest> getProductOrderRequests() {
        return productOrderRequests;
    }
}
