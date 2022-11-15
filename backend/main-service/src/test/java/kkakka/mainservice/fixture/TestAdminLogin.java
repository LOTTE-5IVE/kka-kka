package kkakka.mainservice.fixture;

import java.util.HashMap;
import java.util.Map;
import kkakka.mainservice.admin.application.AdminService;
import kkakka.mainservice.admin.ui.AdminController;
import kkakka.mainservice.admin.ui.dto.AdminUserRequest;
import kkakka.mainservice.common.auth.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

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
                    AdminUserRequest adminUserRequest) {
                Map<String, String> result = new HashMap<>();
                result.put("adminToken", jwtTokenProvider.generateToken("kkakka-test"));
                return ResponseEntity.ok(result);
            }
        };
    }
}
