package kkakka.mainservice.auth.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.auth.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserProfile {

    private String id;
    @JsonProperty(value = "nickname")
    private String name;
    private String email;
    @JsonProperty(value = "age")
    private String ageGroup;
    @JsonProperty(value = "mobile")
    private String phone;

    public ProviderName providerName() {
        return ProviderName.NAVER;
    }
}
