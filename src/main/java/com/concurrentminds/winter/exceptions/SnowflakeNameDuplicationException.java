package com.concurrentminds.winter.exceptions;

public class SnowflakeNameDuplicationException extends Exception {
    public SnowflakeNameDuplicationException(final String snowflakeName) {
        super(String.format("Snowflake `%s` already exists", snowflakeName));
    }
}
