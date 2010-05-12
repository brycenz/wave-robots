package com.wavenz.robots.mvc.controller;

import com.wavenz.robots.mvc.common.Notification;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Command<C> {
    void execute(Notification<C> notification);
}
