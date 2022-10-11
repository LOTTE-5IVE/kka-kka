package kkakka.mainservice.order.ui.dto;

import kkakka.mainservice.order.application.dto.ProductOrderDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<ProductOrderDto> productOrders;
}
