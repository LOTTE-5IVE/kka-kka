package kkakka.mainservice.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginMember {

    private Long id;
    private Authority authority;

    public LoginMember(Authority authority) {
        this(null, authority);
    }

    public boolean isAnonymous() {
        return this.authority.isAnonymous();
    }

    public boolean isMember() {
        return this.authority.isMember();
    }

    public boolean isAdmin() {
        return this.authority.isAdmin();
    }
}
