package kkakka.mainservice.member.auth.ui;

public enum Authority {

    ANONYMOUS,
    MEMBER;

    boolean isAnonymous() {
        return this.equals(Authority.ANONYMOUS);
    }

    boolean isMember() {
        return this.equals(Authority.MEMBER);
    }
}