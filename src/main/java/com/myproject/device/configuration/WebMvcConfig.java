package com.myproject.device.configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myproject.device.configuration.serdeprovider.SerdeProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
class WebMvcConfig implements WebMvcConfigurer {
    private final List<SerdeProvider<?>> serdeProviders;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (SerdeProvider<?> provider : serdeProviders) {
            log.info("Add custom formatter for field type '{}'", provider.getType());
            registry.addFormatterForFieldType(provider.getType(), provider.getTypedFieldFormatter());
        }
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modules(new Jdk8Module(), new JavaTimeModule(), customSerDeModule(), new JsonNullableModule())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public com.fasterxml.jackson.databind.Module customSerDeModule() {
        final var module = new SimpleModule("Custom SerDe module");
        for (SerdeProvider provider : serdeProviders) {
            log.info("Add custom serde for type '{}'", provider.getType());
            module.addSerializer(provider.getType(), provider.getJsonSerializer());
            module.addDeserializer(provider.getType(), provider.getJsonDeserializer());
        }
        return module;
    }
}
