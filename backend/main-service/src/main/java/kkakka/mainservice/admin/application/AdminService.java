package kkakka.mainservice.admin.application;

import kkakka.mainservice.admin.ui.dto.AdminUserRequest;
import kkakka.mainservice.member.auth.exception.AuthorizationException;
import kkakka.mainservice.member.auth.util.JwtTokenProvider;
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

    private final JwtTokenProvider jwtTokenProvider;

    public String requiredLogin(AdminUserRequest adminUserRequest) {
        if (adminUserRequest.getUserId().equals(adminId)
                && adminUserRequest.getPassword().equals(adminPassword)) {
            return jwtTokenProvider.generateToken(adminId);
        }
        throw new AuthorizationException();
    }
}
