package kkakka.mainservice.fixture;

public enum TestAdminUser {

    TEST_ADMIN("test", "test");

    final String userId;
    final String password;

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
