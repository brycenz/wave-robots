package com.wavenz.robots.mvc.controller;

import com.google.common.collect.Maps;
import com.wavenz.robots.mvc.Facade;
import com.wavenz.robots.mvc.common.Notification;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class ControllerImpl implements Controller {
    private static final Logger LOG = Logger.getLogger(ControllerImpl.class);

    private Map<Enum, Class<? extends Command>> commands = Maps.newHashMap();
    private Facade facade;
    private CommandObserver observer;

    public ControllerImpl(Facade facade) {
        this.facade = facade;
        observer = new CommandObserver(this);
    }

    @Override
    public void registerCommand(Enum type, Class<? extends Command> commandClass) {
        LOG.debug("Register Command: " + type);
        if (!commands.containsKey(type)) {
            facade.addObserver(type, observer);
        }
        commands.put(type, commandClass);
    }

    @Override
    public void executeCommand(Notification notification) {
        LOG.debug("Execute Command: " + notification.getType());
        Class<? extends Command> commandClass = commands.get(notification.getType());
        if (commandClass != null) {
            LOG.debug("\tclass: " + commandClass.getSimpleName());
            try {
                Command command = commandClass.newInstance();
                if (command instanceof AbstractCommand) ((AbstractCommand)command).setFacade(facade);
                command.execute(notification);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeCommand(Enum type) {
        commands.remove(type);
        facade.removeObserver(type, observer);
    }

    @Override
    public boolean hasCommand(Enum type) {
        return commands.containsKey(type);
    }
}
