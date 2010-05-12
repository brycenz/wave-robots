package com.wavenz.robots.mvc.model;

import com.wavenz.robots.mvc.Facade;
import com.wavenz.robots.mvc.common.NotificationImpl;
import com.wavenz.robots.mvc.common.Notifier;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public abstract class AbstractProxy<N> implements Proxy, Notifier<N> {
    private Class proxyClass;
    private Facade facade;

    protected AbstractProxy(Class proxyClass) {
        this.proxyClass = proxyClass;
    }

    void setFacade(Facade facade) {
        this.facade = facade;
    }

    @Override
    public Class getProxyClass() {
        return proxyClass;
    }

    @Override
    public void onRegister() {
    }

    @Override
    public void onRemove() {
    }

    @Override
    public void sendNotification(Enum type, N body) {
        facade.notifyObservers(new NotificationImpl<N>(type, body));
    }
}
