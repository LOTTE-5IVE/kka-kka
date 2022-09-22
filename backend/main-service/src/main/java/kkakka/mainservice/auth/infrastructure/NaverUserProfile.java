package kkakka.mainservice.auth.infrastructure;

import kkakka.mainservice.auth.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverUserProfile {

    private String id;
    private String nickname;
    private String email;
    private String age;
    private String mobile;

    public ProviderName providerName() {
        return ProviderName.NAVER;
    }
}
