package kkakka.mainservice.order.ui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipientResponse {

    private String name;
    private String phone;
    private String address;

    public static RecipientResponse create(String name, String phone, String address) {
        return new RecipientResponse(name, phone, address);
    }
}
