package com.wavenz.robots.mvc.view;

import com.wavenz.robots.mvc.common.Notification;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Observer {
    void notifyObserver(Notification notification);
}
