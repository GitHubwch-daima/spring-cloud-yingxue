package com.azure.exception;

/**
 * 自定义的token异常
 */
public class IllegalTokenException extends RuntimeException {

    public IllegalTokenException(String message) {
        super(message);
    }
}
