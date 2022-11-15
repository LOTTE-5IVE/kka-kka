package kkakka.mainservice.admin.ui;

import java.util.HashMap;
import java.util.Map;
import kkakka.mainservice.admin.application.AdminService;
import kkakka.mainservice.admin.ui.dto.AdminUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("!test")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AdminUserRequest adminUserRequest) {
        final String token = adminService.requiredLogin(adminUserRequest);
        final Map<String, String> result = new HashMap<>();
        result.put("adminToken", token);
        return ResponseEntity.ok().body(result);
    }
}
