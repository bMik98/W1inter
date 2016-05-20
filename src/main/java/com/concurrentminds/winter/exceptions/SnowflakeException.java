package com.concurrentminds.winter.exceptions;

public class SnowflakeException extends Exception {
    public SnowflakeException(final String errorDescription) {
        super(errorDescription);
    }
}
