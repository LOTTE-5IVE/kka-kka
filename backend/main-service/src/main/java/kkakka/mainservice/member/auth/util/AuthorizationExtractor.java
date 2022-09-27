package kkakka.mainservice.member.auth.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static String BEARER = "Bearer";
    public static final String ACCESS_TOKEN_TYPE =
            AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(HttpServletRequest request) {
        final Enumeration<String> headers = request.getHeaders(AUTHORIZATION_HEADER);
        while (headers.hasMoreElements()) {
            final String headerValue = headers.nextElement();

            if ((headerValue.toLowerCase().startsWith(BEARER.toLowerCase()))) {
                String authHeaderValue = headerValue.substring(BEARER.length()).trim();
                request.setAttribute(
                        ACCESS_TOKEN_TYPE,
                        headerValue.substring(0, BEARER.length()).trim()
                );
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
