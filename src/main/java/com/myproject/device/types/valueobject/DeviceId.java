package com.myproject.device.types.valueobject;

import java.io.Serializable;
import java.util.UUID;

public class DeviceId implements Serializable {

    private UUID deviceId;

    private DeviceId() {}

    private DeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public static DeviceId of(String deviceId) {
        return new DeviceId(UUID.fromString(deviceId));
    }

    public static DeviceId of(UUID deviceId) {
        return new DeviceId(deviceId);
    }

    public UUID value() {
        return this.deviceId;
    }
}
