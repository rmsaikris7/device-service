package com.myproject.device.types.transport;

import com.myproject.device.types.enums.Brand;
import org.openapitools.jackson.nullable.JsonNullable;

public record ModifyDeviceTO(JsonNullable<String> name, JsonNullable<Brand> brand) {}
