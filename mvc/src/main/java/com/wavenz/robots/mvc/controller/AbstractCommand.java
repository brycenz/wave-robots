package com.wavenz.robots.mvc.controller;

import com.wavenz.robots.mvc.Facade;
import com.wavenz.robots.mvc.common.NotificationImpl;
import com.wavenz.robots.mvc.common.Notifier;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public abstract class AbstractCommand<C,N> implements Command<C>, Notifier<N> {
    protected Facade facade;

    void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void sendNotification(Enum type, N body) {
        facade.notifyObservers(new NotificationImpl<N>(type, body));
    }
}
