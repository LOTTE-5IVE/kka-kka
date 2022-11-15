package kkakka.mainservice.fixture;

public enum TestAdminUser {

    TEST_ADMIN("test", "test");

    String userId;
    String password;

    TestAdminUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
