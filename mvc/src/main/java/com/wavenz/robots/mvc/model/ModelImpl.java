package com.wavenz.robots.mvc.model;

import com.google.common.collect.Maps;
import com.wavenz.robots.mvc.Facade;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class ModelImpl implements Model {
    private static final Logger LOG = Logger.getLogger(ModelImpl.class);

    private Map<Class, Proxy> proxies = Maps.newHashMap();
    private Facade facade;

    public ModelImpl(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void addProxy(Proxy proxy) {
        LOG.debug("Add Proxy: " + proxy.getProxyClass());
        if (proxy instanceof AbstractProxy)  ((AbstractProxy)proxy).setFacade(facade);

        proxies.put(proxy.getProxyClass(), proxy);
    }

    @Override
    public Proxy getProxy(Class proxyClass) {
        return proxies.get(proxyClass);
    }

    @Override
    public <T> T getService(Class<T> proxyClass) {
        //noinspection unchecked
        return (T) getProxy(proxyClass);
    }

    @Override
    public void removeProxy(Class proxyClass) {
        proxies.remove(proxyClass);
    }

    @Override
    public boolean hasProxy(Class proxyClass) {
        return proxies.containsKey(proxyClass);
    }
}
