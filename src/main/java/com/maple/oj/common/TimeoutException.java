package com.maple.oj.common;

public class TimeoutException extends Exception {
    public TimeoutException() {
        super("超时了!!!");
    }
}
