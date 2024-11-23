package com.myproject.device.exceptionhandling;

public class DeviceFailedValidationException extends BusinessException {

    private static final String MESSAGE = "Validation failed. Please check parameters";

    public DeviceFailedValidationException() {
        super(MESSAGE);
    }
}
