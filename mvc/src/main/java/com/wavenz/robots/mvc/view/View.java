package com.wavenz.robots.mvc.view;

import com.wavenz.robots.mvc.common.Notification;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface View {
    void addMediator(Mediator<?> mediator);
    Mediator getMediator(Class<? extends Mediator> mediatorClass);
    void removeMediator(Class<? extends Mediator> mediatorClass);
    boolean hasMediator(Class<? extends Mediator> mediatorClass);
    void addObserver(Enum type, Observer observer);
    void removeObserver(Enum type, Observer observer);
    void notifyObservers(Notification notification);
}
