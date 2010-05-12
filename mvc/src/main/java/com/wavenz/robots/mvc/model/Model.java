package com.wavenz.robots.mvc.model;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Model {
    void addProxy(Proxy proxy);
    Proxy getProxy(Class proxyClass);
    <T> T getService(Class<T> proxyClass);
    void removeProxy(Class proxyClass);
    boolean hasProxy(Class proxyClass);
}
