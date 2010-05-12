package com.wavenz.robots.ripplie.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.ripplie.data.RippleSession;
import com.wavenz.robots.ripplie.proxies.RippleManager;

import java.util.List;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class CreateSession extends AbstractMediator<Event, Object> {
    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.WAVELET_SELF_ADDED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        RippleManager rippleManager = facade.getService(RippleManager.class);
        rippleManager.createSession(wavelet.getWaveId(), wavelet.getWaveletId());
    }
}