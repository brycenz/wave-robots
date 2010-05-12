package com.wavenz.robots.mvc.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.LifeCycle;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.AbstractMediator;

import java.util.List;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class WaveletUpdate extends AbstractMediator<RobotMessageBundle, Event> {
    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(LifeCycle.PROCESS);
    }

    @Override
    public void handleNotification(Notification<RobotMessageBundle> notification) {
        if (notification.getType().equals(LifeCycle.PROCESS)) {
            RobotMessageBundle bundle = notification.getBody();
            for (Event e : bundle.getEvents()) {
                sendNotification(e.getType(), e);
            }
        }
    }
}
