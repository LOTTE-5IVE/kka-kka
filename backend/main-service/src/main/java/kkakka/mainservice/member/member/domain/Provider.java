package kkakka.mainservice.member.member.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provider provider = (Provider) o;
        return Objects.equals(providerId, provider.providerId)
            && providerName == provider.providerName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, providerName);
    }
}
