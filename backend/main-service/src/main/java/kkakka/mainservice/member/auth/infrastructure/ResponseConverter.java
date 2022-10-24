package kkakka.mainservice.member.auth.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseConverter {

    private final ObjectMapper objectMapper;

    public <T> T extractDataAsAccount(String json, Class<T> profileType) {
        try {
            return objectMapper.readValue(json, profileType);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
