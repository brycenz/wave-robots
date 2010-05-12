package com.wavenz.robots.mvc.common;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Notifier<T> {
    void sendNotification(Enum type, T body);
}
