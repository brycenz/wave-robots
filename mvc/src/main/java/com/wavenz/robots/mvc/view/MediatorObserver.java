package com.wavenz.robots.mvc.view;

import com.wavenz.robots.mvc.common.Notification;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class MediatorObserver implements Observer {
    private Mediator mediator;

    public MediatorObserver(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void notifyObserver(Notification notification) {
        mediator.handleNotification(notification);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediatorObserver that = (MediatorObserver) o;

        if (!mediator.equals(that.mediator)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mediator.hashCode();
    }
}
