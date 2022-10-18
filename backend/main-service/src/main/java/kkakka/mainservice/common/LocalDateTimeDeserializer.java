package kkakka.mainservice.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss");

    public LocalDateTimeDeserializer() {
        this(null);
    }

    public LocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) {
        try {
            String date = jsonParser.getText();
            return LocalDateTime.from(FORMATTER.parse(date));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}