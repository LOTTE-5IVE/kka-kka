package kkakka.authservice.auth.infrastructure.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleTokenRequest {

    @JsonProperty(value = "grant_type")
    private String grantType;
    @JsonProperty(value = "redirect_uri")
    private String redirectUri;
    @JsonProperty(value = "client_id")
    private String clientId;
    @JsonProperty(value = "client_secret")
    private String clientSecret;
    private String scope;
    private String code;
}
