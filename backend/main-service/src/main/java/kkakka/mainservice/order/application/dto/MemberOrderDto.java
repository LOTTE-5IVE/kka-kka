package kkakka.mainservice.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.member.member.ui.dto.OrderResponse;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.Recipient;
import kkakka.mainservice.order.ui.dto.RecipientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderDto {

    private Long id;
    private LocalDateTime orderedAt;
    private Integer totalPrice;
    private List<MemberProductOrderDto> memberProductOrderDto;
    private RecipientDto recipientDto;

    public static MemberOrderDto toDto(Order order,
            List<MemberProductOrderDto> memberProductOrderDto) {
        return new MemberOrderDto(
                order.getId(), order.getOrderedAt(), order.getTotalPrice(),
                memberProductOrderDto,
                RecipientDto.toDto(order.getRecipient())
        );
    }

    public OrderResponse toResponseDto() {
        return OrderResponse.create(
                this.id,
                this.orderedAt,
                this.totalPrice,
                this.memberProductOrderDto.stream()
                        .map(MemberProductOrderDto::toResponse)
                        .collect(Collectors.toList()),
                this.recipientDto.toResponse()
        );
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RecipientDto {

        private String name;
        private String phone;
        private String address;

        public static RecipientDto toDto(Recipient recipient) {
            return new RecipientDto(recipient.getName(), recipient.getPhone(),
                    recipient.getAddress());
        }

        public RecipientResponse toResponse() {
            return RecipientResponse.create(this.name, this.phone, this.address);
        }
    }
}
