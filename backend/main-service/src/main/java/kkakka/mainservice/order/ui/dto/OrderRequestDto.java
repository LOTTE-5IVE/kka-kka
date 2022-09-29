package kkakka.mainservice.order.ui.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {

    private Long memberId;
    private List<ProductOrderRequest> productOrderRequests;

    //==테스트용 생성자==//
    private OrderRequestDto() {
    }

    public OrderRequestDto(Long memberId, List<ProductOrderRequest> productOrderRequests) {
        this.memberId = memberId;
        this.productOrderRequests = productOrderRequests;
    }
}
