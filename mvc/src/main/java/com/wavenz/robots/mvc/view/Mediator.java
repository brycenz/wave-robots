package com.wavenz.robots.mvc.view;

import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.common.Registerable;

import java.util.List;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Mediator<B> extends Registerable {
    List<? extends Enum> getNotificationInterests();
    void handleNotification(Notification<B> notification);
}
