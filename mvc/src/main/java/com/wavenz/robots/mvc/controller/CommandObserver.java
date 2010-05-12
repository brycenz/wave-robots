package com.wavenz.robots.mvc.controller;

import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.Observer;
import org.apache.log4j.Logger;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class CommandObserver implements Observer {
    private static final Logger LOG = Logger.getLogger(CommandObserver.class);

    private Controller controller;

    public CommandObserver(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void notifyObserver(Notification notification) {
        LOG.debug("Notify Observer: " + notification.getType());
        controller.executeCommand(notification);
    }
}
