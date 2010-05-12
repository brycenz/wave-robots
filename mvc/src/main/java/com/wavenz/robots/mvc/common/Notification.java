package com.wavenz.robots.mvc.common;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Notification<T> {
    Enum getType();
    T getBody();
}
