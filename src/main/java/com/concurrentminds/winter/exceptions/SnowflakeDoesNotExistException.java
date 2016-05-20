package com.concurrentminds.winter.exceptions;

public class SnowflakeDoesNotExistException extends Exception{
    public SnowflakeDoesNotExistException(final String snowflakeName) {
        super(String.format("Snowflake `%s` does not exist", snowflakeName));
    }
}
