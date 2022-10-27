package kkakka.mainservice.order.ui.dto;

import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.application.dto.RecipientDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private RecipientRequest recipientRequest;
    private List<ProductOrderDto> productOrders;

    public RecipientDto toRecipientDto() {
        return RecipientDto.create(
                recipientRequest.getName(),
                recipientRequest.getPhone(),
                recipientRequest.getAddress()
        );
    }
}
