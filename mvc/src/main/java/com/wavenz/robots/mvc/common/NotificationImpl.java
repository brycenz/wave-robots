package com.wavenz.robots.mvc.common;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class NotificationImpl<T> implements Notification<T> {
    private Enum type;
    private T body;

    public NotificationImpl(Enum type, T body) {
        this.type = type;
        this.body = body;
    }

    @Override
    public Enum getType() {
        return type;
    }

    @Override
    public T getBody() {
        return body;
    }
}
