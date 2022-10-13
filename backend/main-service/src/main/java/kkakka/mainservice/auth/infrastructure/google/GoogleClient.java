package kkakka.mainservice.auth.infrastructure.google;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import kkakka.mainservice.auth.application.SocialClient;
import kkakka.mainservice.auth.application.UserProfile;
import kkakka.mainservice.auth.application.dto.SocialProviderCodeDto;
import kkakka.mainservice.auth.infrastructure.ClientResponseConverter;
import kkakka.mainservice.auth.infrastructure.google.dto.GoogleTokenRequest;
import kkakka.mainservice.auth.infrastructure.google.util.BirthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleClient implements SocialClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-type";
    private static final String DEFAULT_CHARSET = "application/x-www-form-urlencoded;charset=utf-8";

    private static final String GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String BEARER = "Bearer %s";

    private final ClientResponseConverter converter;
    private final RestTemplate restTemplate;
    private final GoogleOauthInfo googleOauthInfo;
    private final ObjectMapper objectMapper;

    @Override
    public UserProfile getUserProfile(SocialProviderCodeDto socialProviderCodeDto) {
        final String accessToken = accessToken(socialProviderCodeDto.getCode());

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION_HEADER, String.format(BEARER, accessToken));

        final ResponseEntity<String> response = restTemplate.exchange(
                googleOauthInfo.getProfileRequestUrl(), HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String.class
        );
        return converter.extractDataAsAccount(
                convertResponseToUserProfile(response),
                GoogleUserProfile.class
        );
    }

    private String accessToken(String code) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE_HEADER, DEFAULT_CHARSET);

        final ResponseEntity<String> response = restTemplate.exchange(
                googleOauthInfo.getAccessTokenRequestUrl(), HttpMethod.POST,
                new HttpEntity<>(
                        converter.convertHttpBody(
                                new GoogleTokenRequest(
                                        GRANT_TYPE,
                                        googleOauthInfo.getRedirectUri(),
                                        googleOauthInfo.getClientId(),
                                        googleOauthInfo.getClientKey(),
                                        googleOauthInfo.getScope(),
                                        code
                                )
                        ),
                        httpHeaders), String.class
        );
        return converter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }

    private String convertResponseToUserProfile(ResponseEntity<String> response) {
        try {
            final JsonObject jsonObject = new JsonObject();

            final JsonNode jsonNode = objectMapper.readTree(response.getBody());
            if (jsonNode.has("phoneNumbers")) {
                jsonObject.addProperty("phone",
                        parseValidString(jsonNode.get("phoneNumbers").get(0).get("value")));
            }

            if (jsonNode.has("nicknames")) {
                jsonObject.addProperty("name",
                        parseValidString(jsonNode.get("nicknames").get(0).get("value")));
            }

            if (jsonNode.has("birthdays")) {
                jsonObject.addProperty("ageGroup",
                        BirthConverter.convertToAgeGroup(
                                parseValidString(
                                        jsonNode.get("birthdays").get(0).get("date").get("year"))
                        )
                );
            }

            if (jsonNode.has("emailAddresses")) {
                jsonObject.addProperty("email",
                        parseValidString(jsonNode.get("emailAddresses").get(0).get("value")));
            }

            jsonObject.addProperty("id",
                    parseProviderId(parseValidString(jsonNode.get("resourceName"))));

            return jsonObject.toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private String parseValidString(JsonNode value) {
        return value.toString().replaceAll("\"", "");
    }

    private String parseProviderId(String value) {
        return value.split("/")[1];
    }

}
