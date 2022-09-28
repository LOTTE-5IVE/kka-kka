package kkakka.mainservice.member.auth.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class ResponseConverter {

    private final ObjectMapper objectMapper;

    public <T> MultiValueMap<String, String> convertHttpBody(T body) {
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        final Map<String, String> bodyMap = objectMapper.convertValue(body, new TypeReference<>() {
        });
        params.setAll(bodyMap);
        return params;
    }

    public <T> T extractDataAsAccount(String json, Class<T> profileType) {
        try {
            return objectMapper.readValue(json, profileType);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
