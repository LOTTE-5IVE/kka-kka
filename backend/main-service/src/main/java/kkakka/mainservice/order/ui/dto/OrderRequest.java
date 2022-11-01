package kkakka.mainservice.order.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("recipient")
    private RecipientRequest recipientRequest;
    private List<ProductOrderDto> productOrders;

    public RecipientDto toRecipientDto() {
        return RecipientDto.create(
                recipientRequest.getName(),
                recipientRequest.getEmail(),
                recipientRequest.getPhone(),
                recipientRequest.getAddress()
        );
    }
}
