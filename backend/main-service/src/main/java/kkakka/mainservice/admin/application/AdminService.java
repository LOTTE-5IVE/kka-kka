package kkakka.mainservice.admin.application;

import kkakka.mainservice.admin.ui.dto.AdminUserRequest;
import kkakka.mainservice.common.auth.util.JwtTokenProvider;
import kkakka.mainservice.common.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Value("${admin.user.id}")
    private String adminId;
    @Value("${admin.user.password}")
    private String adminPassword;
    @Value("${admin.user.key}")
    private String adminKey;

    private final JwtTokenProvider jwtTokenProvider;

    public String requiredLogin(AdminUserRequest adminUserRequest) {
        if (adminUserRequest.getUserId().equals(adminId)
                && adminUserRequest.getPassword().equals(adminPassword)) {
            return jwtTokenProvider.generateToken(adminKey);
        }
        throw new LoginFailException();
    }
}
