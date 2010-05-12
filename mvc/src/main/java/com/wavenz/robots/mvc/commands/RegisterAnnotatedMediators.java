package com.wavenz.robots.mvc.commands;

import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.controller.AbstractCommand;
import com.wavenz.robots.mvc.view.Mediator;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RegisterAnnotatedMediators extends AbstractCommand<Set<Class<?>>, Object> {
    private static final Logger LOG = Logger.getLogger(RegisterAnnotatedMediators.class);

    @Override
    public void execute(Notification<Set<Class<?>>> notification) {
        for (Class<?> mediator : notification.getBody()) {
            RegisterMediator registration = mediator.getAnnotation(RegisterMediator.class);
            if (registration != null) {
                if (Mediator.class.isAssignableFrom(mediator)) {
                    try {
                        facade.addMediator((Mediator<?>) mediator.newInstance());
                    } catch (Throwable e) {
                        LOG.error("Error instantiating mediator", e);
                    }
                }
                else {
                    LOG.warn("Class annotated with RegisterMediator is not a mediator: " + mediator.getName());
                }
            }
        }
    }
}