package kkakka.mainservice.order.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientRequest {

    private String name;
    private String email;
    private String phone;
    private String address;
}
