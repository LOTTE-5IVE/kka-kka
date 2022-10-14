package kkakka.mainservice.order.application.dto;

import java.util.List;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDto {

    private Long memberId;
    private List<ProductOrderDto> productOrders;

    public static OrderDto create(Long memberId, OrderRequest orderRequest) {
        return new OrderDto(memberId,orderRequest.getProductOrders());
    }
}
