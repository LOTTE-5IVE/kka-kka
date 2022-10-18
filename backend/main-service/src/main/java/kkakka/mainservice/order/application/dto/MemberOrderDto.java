package kkakka.mainservice.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.member.member.ui.dto.OrderResponse;
import kkakka.mainservice.order.domain.Order;
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

    public static MemberOrderDto toDto(Order order,
            List<MemberProductOrderDto> memberProductOrderDto) {
        return new MemberOrderDto(
                order.getId(), order.getOrderedAt(), order.getTotalPrice(),
                memberProductOrderDto
        );
    }

    public OrderResponse toResponseDto() {
        return OrderResponse.create(
                this.id,
                this.orderedAt,
                this.totalPrice,
                this.memberProductOrderDto.stream()
                        .map(MemberProductOrderDto::toResponse)
                        .collect(Collectors.toList())
        );
    }
}
