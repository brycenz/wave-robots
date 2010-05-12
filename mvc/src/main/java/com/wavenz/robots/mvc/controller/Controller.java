package com.wavenz.robots.mvc.controller;

import com.wavenz.robots.mvc.common.Notification;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Controller {
    void registerCommand(Enum type, Class<? extends Command> commandClass);
    void executeCommand(Notification notification);
    void removeCommand(Enum type);
    boolean hasCommand(Enum type);
}
