package kkakka.mainservice.order.ui.dto;

import kkakka.mainservice.member.domain.Member;

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

    //==테스트용 생성자==//
    public OrderRequestDto(Long memberId, List<ProductOrderRequest> productOrderRequests) {
        this.memberId = memberId;
        this.productOrderRequests = productOrderRequests;
    }
}
