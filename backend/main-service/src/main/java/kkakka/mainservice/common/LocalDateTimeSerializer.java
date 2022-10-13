package kkakka.mainservice.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}