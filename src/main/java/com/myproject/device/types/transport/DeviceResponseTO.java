package com.myproject.device.types.transport;

import com.myproject.device.types.enums.Brand;
import com.myproject.device.types.valueobject.DeviceId;
import java.time.Instant;

public record DeviceResponseTO(DeviceId deviceId, String name, Brand brand, Instant createdOn) {}
