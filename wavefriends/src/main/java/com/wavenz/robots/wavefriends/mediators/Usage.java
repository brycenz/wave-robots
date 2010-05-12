package com.wavenz.robots.wavefriends.mediators;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.Wavelet;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.proxies.TemplateRenderer;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavefriends.proxies.AccountManager;

import java.util.List;

import static com.wavenz.robots.util.WaveUtil.isNewWave;
import static com.wavenz.robots.wavefriends.Util.isNewAccount;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class Usage extends AbstractMediator<Event, Object> {
    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.WAVELET_SELF_ADDED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        AccountManager accountManager = facade.getService(AccountManager.class);
        if (!accountManager.isCurrentAccount(notification.getBody().getModifiedBy()) &&
            !isNewWave(wavelet) && !SignUp.isSignUpBlip(wavelet.getRootBlip())
            && notification.getType().equals(EventType.WAVELET_SELF_ADDED)) {
//            Blip blip = wavelet.appendBlip(); Currently this appends text twice 
            Blip blip = wavelet.getRootBlip().createChild();
            TemplateRenderer renderer = facade.getService(TemplateRenderer.class);
            blip.getDocument().appendMarkup(renderer.renderTemplate("Usage.ftl"));
        }
    }
}