package com.wavenz.robots.mvc.commands;

import com.wavenz.robots.mvc.annotations.RegisterProxy;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.controller.AbstractCommand;
import com.wavenz.robots.mvc.model.Proxy;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RegisterAnnotatedProxies extends AbstractCommand<Set<Class<?>>, Object> {
    private static final Logger LOG = Logger.getLogger(RegisterAnnotatedProxies.class);

    @Override
    public void execute(Notification<Set<Class<?>>> notification) {
        for (Class<?> proxy : notification.getBody()) {
            RegisterProxy registration = proxy.getAnnotation(RegisterProxy.class);
            if (registration != null) {
                if (registration != null && Proxy.class.isAssignableFrom(proxy)) {
                    try {
                        facade.addProxy((Proxy) proxy.newInstance());
                    } catch (Throwable e) {
                        LOG.error("Error instantiating proxy", e);
                    }
                }
                else {
                    LOG.warn("Class annotated with RegisterProxy is not a proxy: " + proxy.getName());
                }
            }
        }
    }
}