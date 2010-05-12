package com.wavenz.robots.mvc.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wavenz.robots.mvc.Facade;
import com.wavenz.robots.mvc.common.Notification;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class ViewImpl implements View {
    private static final Logger LOG = Logger.getLogger(ViewImpl.class);

    private Map<Class<? extends Mediator>, Mediator<?>> mediators = Maps.newHashMap();
    private Map<Enum, List<Observer>> observers = Maps.newHashMap();
    private Facade facade;

    public ViewImpl(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void addMediator(Mediator<?> mediator) {
        LOG.debug("Add Mediator: " + mediator.getClass().getSimpleName());
        if (mediator instanceof AbstractMediator)  ((AbstractMediator)mediator).setFacade(facade);

        mediators.put(mediator.getClass(), mediator);
        MediatorObserver observer = new MediatorObserver(mediator);
        for (Enum type : mediator.getNotificationInterests()) {
            addObserver(type, observer);
        }
    }

    @Override
    public Mediator getMediator(Class<? extends Mediator> mediatorClass) {
        return mediators.get(mediatorClass);
    }

    @Override
    public void removeMediator(Class<? extends Mediator> mediatorClass) {
        Mediator<?> mediator = mediators.remove(mediatorClass);
        if (mediator != null) {
            MediatorObserver observer = new MediatorObserver(mediator);
            for (Enum type : mediator.getNotificationInterests()) {
                removeObserver(type, observer);
            }
        }
    }

    @Override
    public boolean hasMediator(Class<? extends Mediator> mediatorClass) {
        return mediators.containsKey(mediatorClass);
    }

    @Override
    public void addObserver(Enum type, Observer observer) {
        LOG.debug("Add Observer: " + type);
        if (!observers.containsKey(type)) {
            observers.put(type, Lists.<Observer>newArrayList(observer));
        }
        else if (!observers.get(type).contains(observer)) {
            observers.get(type).add(observer);
        }
    }

    @Override
    public void removeObserver(Enum type, Observer observer) {
        if (observers.containsKey(type) && observers.get(type).contains(observer)) {
            observers.get(type).remove(observer);
        }
    }

    @Override
    public void notifyObservers(Notification notification) {
        LOG.debug("Notify Observers: " + notification.getType());
        if (observers.containsKey(notification.getType())) {
            LOG.debug("\t" + observers.get(notification.getType()).size() + " observers");
            for (Observer observer : observers.get(notification.getType())) {
                observer.notifyObserver(notification);
            }
        }
    }
}
