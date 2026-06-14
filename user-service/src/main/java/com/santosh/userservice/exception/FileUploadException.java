package com.santosh.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FileUploadException extends RuntimeException{
    private final String message;
    @Getter
    private final HttpStatus status;

    public FileUploadException(String message) {
        this.message = message;
        this.status = HttpStatus.NOT_ACCEPTABLE;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
