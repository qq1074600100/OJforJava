package com.maple.oj.common;

public class CodeRuntimeException extends Exception {
    public CodeRuntimeException(String msg) {
        super("运行异常\n" + msg);
    }
}
