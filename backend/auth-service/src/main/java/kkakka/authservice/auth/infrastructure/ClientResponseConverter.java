package kkakka.authservice.auth.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ClientResponseConverter {

    private final ObjectMapper objectMapper;

    public <T> MultiValueMap<String, String> convertHttpBody(T body) {
        final LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        final Map<String, String> bodyMap = objectMapper.convertValue(body, new TypeReference<>() {
        });
        params.setAll(bodyMap);
        return params;
    }

    public String extractDataAsString(String json, String dataName) {
        try {
            return objectMapper.readTree(json).get(dataName).asText();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    public <T> T extractDataAsAccount(String json, Class<T> profileType) {
        try {
            return objectMapper.readValue(json, profileType);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
