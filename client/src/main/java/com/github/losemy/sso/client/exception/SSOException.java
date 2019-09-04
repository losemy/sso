package com.github.losemy.sso.client.exception;

/**
 * Description: sso
 * todo 健全返回码设计
 * @author lose on 2019/9/4 9:36
 */
public class SSOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SSOException(String message) {
        super(message);
    }

    public SSOException(Throwable throwable) {
        super(throwable);
    }

    public SSOException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
