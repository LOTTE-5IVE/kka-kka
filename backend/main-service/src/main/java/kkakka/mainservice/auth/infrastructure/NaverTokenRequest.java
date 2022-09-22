package kkakka.mainservice.auth.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverTokenRequest {

    private String grant_type;
    private String client_id;
    private String client_secret;
    private String code;
    private String state;
}
