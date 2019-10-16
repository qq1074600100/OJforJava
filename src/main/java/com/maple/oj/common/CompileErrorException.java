package com.maple.oj.common;

public class CompileErrorException extends Exception {
    public CompileErrorException(String msg) {
        super("编译错误\n" + msg);
    }
}
