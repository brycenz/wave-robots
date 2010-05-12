package com.wavenz.robots.wavefriends.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.proxies.TemplateRenderer;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavefriends.NotificationType;
import com.wavenz.robots.wavefriends.data.User;
import com.wavenz.robots.wavefriends.proxies.AccountManager;

import java.util.List;

import static com.wavenz.robots.util.WaveUtil.isNewWave;
import static com.wavenz.robots.util.WaveUtil.isRootBlip;
import static com.wavenz.robots.wavefriends.mediators.SignUp.isSignUpBlip;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class Account extends AbstractMediator<Event, Object> {
    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.WAVELET_SELF_ADDED, EventType.BLIP_SUBMITTED, NotificationType.DISPLAY_ACCOUNT);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        Blip blip = notification.getBody().getBlip();
        TextView document = blip.getDocument();
        AccountManager accountManager = facade.getService(AccountManager.class);
        if (accountManager.isCurrentAccount(notification.getBody().getModifiedBy())) {
            if (isSignUpBlip(blip) && notification.getType().equals(NotificationType.DISPLAY_ACCOUNT)) {
                document.delete();
                displayAccount(wavelet, document);
            }
            else if (isRootBlip(blip) && isNewWave(wavelet) && notification.getType().equals(EventType.WAVELET_SELF_ADDED)) {
                displayAccount(wavelet, document);
            }
        }
    }

    private void displayAccount(Wavelet wavelet, TextView document) {
        wavelet.setTitle(new StyledText("Wave Friends - Account", StyleType.HEADING2));
        TemplateRenderer renderer = facade.getService(TemplateRenderer.class);
        document.appendMarkup(renderer.renderTemplate("Account.ftl"));
    }
}