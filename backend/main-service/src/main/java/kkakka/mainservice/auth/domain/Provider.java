package kkakka.mainservice.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Provider {

    private String providerId;
    private ProviderName providerName;

    public static Provider create(String providerId, ProviderName providerName) {
        return new Provider(providerId, providerName);
    }
}
