package kkakka.mainservice.order.ui.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private Long memberId;
    private List<ProductOrderRequest> productOrderRequests;

    //==테스트용 생성자==//
    private OrderRequest() {
    }

    public OrderRequest(Long memberId, List<ProductOrderRequest> productOrderRequests) {
        this.memberId = memberId;
        this.productOrderRequests = productOrderRequests;
    }
}
