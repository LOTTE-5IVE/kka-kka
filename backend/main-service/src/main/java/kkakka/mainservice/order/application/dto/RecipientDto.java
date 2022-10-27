package kkakka.mainservice.order.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipientDto {

    private String name;
    private String phone;
    private String address;

    public static RecipientDto create(String name, String phone, String address) {
        return new RecipientDto(name, phone, address);
    }
}
