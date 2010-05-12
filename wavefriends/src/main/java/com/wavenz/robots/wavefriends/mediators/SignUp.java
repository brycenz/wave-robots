package com.wavenz.robots.wavefriends.mediators;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.proxies.TemplateRenderer;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavefriends.NotificationType;
import com.wavenz.robots.wavefriends.data.User;
import com.wavenz.robots.wavefriends.proxies.AccountManager;

import java.util.List;
import java.util.Map;

import static com.wavenz.robots.util.WaveUtil.isNewWave;
import static com.wavenz.robots.util.WaveUtil.isRootBlip;
import static com.wavenz.robots.wavefriends.Util.isNewAccount;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class SignUp extends AbstractMediator<Event, Object> {
    public static final String SIGN_UP_BLIP = "wavefriends.signup.blip";
    public static final String SIGN_UP_GADGET_URL = "http://wavefriends.appspot.com/gadgets/SignUp.xml";
    public static final String SIGN_UP_USER = "wavefriends.signup.user";

    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.WAVELET_SELF_ADDED, EventType.BLIP_SUBMITTED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        Blip blip = notification.getBody().getBlip();
        TextView document = blip.getDocument();
        AccountManager accountManager = facade.getService(AccountManager.class);
        if (!accountManager.isCurrentAccount(notification.getBody().getModifiedBy())) {
            if (isRootBlip(blip) && isNewWave(wavelet) && notification.getType().equals(EventType.WAVELET_SELF_ADDED)) {
                wavelet.setTitle(new StyledText("Wave Friends - Social networking for Wave", StyleType.HEADING2));

                TemplateRenderer renderer = facade.getService(TemplateRenderer.class);
                document.appendMarkup(renderer.renderTemplate("SignUp.ftl"));

                Gadget gadget = new Gadget(SIGN_UP_GADGET_URL);
                document.getGadgetView().append(gadget);

                wavelet.setDataDocument(SIGN_UP_BLIP, blip.getBlipId());
                wavelet.setDataDocument(SIGN_UP_USER, wavelet.getCreator());
            }
            else if (isSignUpBlip(blip) && notification.getType().equals(EventType.BLIP_SUBMITTED)) {
                Gadget gadget = document.getGadgetView().getGadget(SIGN_UP_GADGET_URL);
                if (gadget != null) {
                    String firstName = gadget.getField("firstName");
                    String lastName = gadget.getField("lastName");
                    sendNotification(NotificationType.CREATE_ACCOUNT, new User(
                        wavelet.getDataDocument(SIGN_UP_USER), firstName, lastName)
                    );
                    sendNotification(NotificationType.DISPLAY_ACCOUNT, notification.getBody());
                }
            }
        }
    }

    public static boolean isSignUpBlip(Blip blip) {
        String blipId = blip.getWavelet().getDataDocument(SIGN_UP_BLIP);
        return blipId != null && blipId.equals(blip.getBlipId());
    }
}
