package com.wavenz.robots.mvc.commands;

import com.wavenz.robots.mvc.annotations.RegisterCommand;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.controller.AbstractCommand;
import com.wavenz.robots.mvc.controller.Command;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RegisterAnnotatedCommands extends AbstractCommand<Set<Class<?>>, Object> {
    private static final Logger LOG = Logger.getLogger(RegisterAnnotatedCommands.class);

    @Override
    public void execute(Notification<Set<Class<?>>> notification) {
        for (Class<?> command : notification.getBody()) {
            RegisterCommand registration = command.getAnnotation(RegisterCommand.class);
            if (registration != null) {
                if (Command.class.isAssignableFrom(command)) {
                    if (Enum.class.isAssignableFrom(registration.enumeration())) {
                        //noinspection unchecked
                        facade.registerCommand(Enum.valueOf(registration.enumeration(), registration.value()), (Class<? extends Command>)command);
                    }
                    else {
                        LOG.warn("Class annotated with RegisterCommand does not have Enum as enumeration: " + registration.enumeration());
                    }
                }
                else {
                    LOG.warn("Class annotated with RegisterCommand is not a command: " + command.getName());
                }
            }
        }
    }
}