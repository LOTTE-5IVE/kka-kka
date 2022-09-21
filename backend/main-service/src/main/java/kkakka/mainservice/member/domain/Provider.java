package kkakka.mainservice.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kkakka.mainservice.auth.domain.ProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Provider {

    @Column
    private String providerId;
    @Column
    @Enumerated(EnumType.STRING)
    private ProviderName providerName;

    public static Provider create(String providerId, ProviderName providerName) {
        return new Provider(providerId, providerName);
    }
}
