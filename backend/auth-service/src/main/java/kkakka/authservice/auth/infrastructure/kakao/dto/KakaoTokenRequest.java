package kkakka.authservice.auth.infrastructure.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoTokenRequest {

    @JsonProperty(value = "grant_type")
    private String grantType;
    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
    @JsonProperty(value = "client_id")
    private String clientId;
    @JsonProperty(value = "client_secret")
    private String clientKey;
    private String code;
}
