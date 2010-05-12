package com.wavenz.robots.ripplie.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.ripplie.data.RippleSession;
import com.wavenz.robots.ripplie.proxies.RippleManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class Script extends AbstractMediator<Event, Object> {
    private static final Logger LOG = Logger.getLogger(Script.class);
    private static final Pattern SCRIPT_PATTERN = Pattern.compile(".*#!ripplie \\{(.*?)\\}.*", Pattern.DOTALL);

    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.BLIP_SUBMITTED, EventType.WAVELET_SELF_ADDED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        Blip submittedBlip = notification.getBody().getBlip();
        TextView document = submittedBlip.getDocument();

        Matcher matcher = SCRIPT_PATTERN.matcher(document.getText());
        if (matcher.matches()) {
            RippleManager rippleManager = facade.getService(RippleManager.class);
            RippleSession session = rippleManager.getSession(wavelet.getWaveId(), wavelet.getWaveletId());
            String result = session.evaluate(matcher.group(1));
            Blip resultBlip = submittedBlip.createChild();
            resultBlip.getDocument().append(result);
        }
    }
}