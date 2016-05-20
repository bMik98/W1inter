package com.concurrentminds.winter.exceptions;

public class SnowflakeDeniedException extends Exception {
    public SnowflakeDeniedException(final String snowflakeName) {
        super(String.format("Access to snowflake `%s` was denied ", snowflakeName));
    }
}
