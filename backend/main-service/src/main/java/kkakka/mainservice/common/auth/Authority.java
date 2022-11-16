package kkakka.mainservice.common.auth;

public enum Authority {

    ANONYMOUS,
    MEMBER,
    ADMIN;

    boolean isAnonymous() {
        return this.equals(Authority.ANONYMOUS);
    }

    boolean isMember() {
        return this.equals(Authority.MEMBER);
    }

    boolean isAdmin() {
        return this.equals(Authority.ADMIN);
    }
}