package kkakka.mainservice.order.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import kkakka.mainservice.order.application.dto.RecipientDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Recipient {

    @Column(name = "recipient_name")
    private String name;
    @Column(name = "recipient_email")
    private String email;
    @Column(name = "recipient_phone")
    private String phone;
    @Column(name = "recipient_address")
    private String address;

    public static Recipient from(RecipientDto recipientDto) {
        return new Recipient(recipientDto.getName(), recipientDto.getEmail(), recipientDto.getPhone(),
                recipientDto.getAddress());
    }
}
