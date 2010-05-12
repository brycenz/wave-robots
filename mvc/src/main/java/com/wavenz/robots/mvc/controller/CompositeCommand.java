package com.wavenz.robots.mvc.controller;

import com.google.common.collect.Lists;
import com.wavenz.robots.mvc.common.Notification;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public abstract class CompositeCommand<C,N> extends AbstractCommand<C,N> {
    private static final Logger LOG = Logger.getLogger(CompositeCommand.class);

    private List<Class<? extends Command<C>>> commandClasses = Lists.newArrayList();

    protected CompositeCommand() {
        initialise();        
    }

    protected abstract void initialise();

    protected void addCommand(Class<? extends Command<C>> commandClass) {
        commandClasses.add(commandClass);
    }

    @Override
    public void execute(Notification<C> notification) {
        LOG.debug("Executing Composite Command");
        for (Class<? extends Command<C>> commandClass : commandClasses) {
            try {
                LOG.debug("\tclass: " + commandClass.getSimpleName());
                Command<C> command = commandClass.newInstance();
                if (command instanceof AbstractCommand) ((AbstractCommand)command).setFacade(facade);
                command.execute(notification);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
