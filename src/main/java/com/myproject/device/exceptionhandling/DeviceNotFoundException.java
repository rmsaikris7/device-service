package com.myproject.device.exceptionhandling;

import java.util.UUID;
import org.springframework.http.HttpStatus;

public class DeviceNotFoundException extends BusinessException {

    private static final String MESSAGE = "The device with id %s not found.";

    public DeviceNotFoundException(UUID deviceId) {
        super(String.format(MESSAGE, deviceId), HttpStatus.NOT_FOUND);
    }
}
