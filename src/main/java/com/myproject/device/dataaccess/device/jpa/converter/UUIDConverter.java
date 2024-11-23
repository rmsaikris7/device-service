package com.myproject.device.dataaccess.device.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.util.UUID;

@Convert
public class UUIDConverter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UUID.fromString(dbData);
    }
}
