package com.myproject.device.exceptionhandling;

import com.myproject.device.types.transport.ErrorTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DeviceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorTO> handleBusinessExceptions(BusinessException ex) {
        return new ResponseEntity<>(new ErrorTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorTO> handleNotFoundExceptions(BusinessException ex) {
        return new ResponseEntity<>(new ErrorTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
