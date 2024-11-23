package com.myproject.device.configuration.serdeprovider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.myproject.device.types.valueobject.DeviceId;
import java.io.IOException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DeviceIdSerdeProvider implements SerdeProvider<DeviceId> {
    @Override
    public JsonDeserializer<DeviceId> getJsonDeserializer() {
        return new JsonDeserializer<>() {
            @Override
            public DeviceId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                final var value = p.getValueAsString();
                if (value == null) {
                    return null;
                }
                return DeviceId.of(value);
            }
        };
    }

    @Override
    public JsonSerializer<DeviceId> getJsonSerializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(DeviceId value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                if (value == null) {
                    gen.writeNull();
                } else {
                    gen.writeString(value.value().toString());
                }
            }
        };
    }

    @Override
    public Formatter<DeviceId> getTypedFieldFormatter() {
        return new Formatter<>() {
            @Override
            public DeviceId parse(String text, Locale locale) {
                return DeviceId.of(text);
            }

            @Override
            public String print(DeviceId object, Locale locale) {
                return object.value().toString();
            }
        };
    }

    @Override
    public Class<DeviceId> getType() {
        return DeviceId.class;
    }
}
