package kkakka.mainservice.fixture;

import static kkakka.mainservice.fixture.TestAdminUser.TEST_ADMIN;

import java.util.HashMap;
import java.util.Map;
import kkakka.mainservice.admin.application.AdminService;
import kkakka.mainservice.admin.ui.AdminController;
import kkakka.mainservice.admin.ui.dto.AdminUserRequest;
import kkakka.mainservice.common.auth.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.spec.internal.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@TestConfiguration
public class TestAdminLogin {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AdminService adminService;

    @Bean
    public AdminController adminController() {
        return new AdminController(adminService) {

            @Override
            public ResponseEntity<Map<String, String>> login(
                    @RequestBody AdminUserRequest adminUserRequest) {
                if (TEST_ADMIN.getUserId().equals(adminUserRequest.getUserId())
                        && TEST_ADMIN.getPassword().equals(adminUserRequest.getPassword())) {
                    Map<String, String> result = new HashMap<>();
                    result.put("adminToken", jwtTokenProvider.generateToken("kkakka-test"));
                    return ResponseEntity.ok(result);
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        };
    }
}
