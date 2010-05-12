package com.wavenz.robots.wavefriends.mediators;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.proxies.TemplateRenderer;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavefriends.data.User;
import com.wavenz.robots.wavefriends.proxies.AccountManager;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class Debug extends AbstractMediator<Event, Object> {
    private Set ALLOWED_DEBUG_ADDRESSES = Sets.newHashSet(
        "brycenz@googlewave.com", "brycenz@wavesandbox.com",
        "testonewavenz@googlewave.com", "testtwowavenz@googlewave.com",
        "testthreewavenz@googlewave.com", "testfourwavenz@googlewave.com"
    );

    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.BLIP_SUBMITTED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        if (ALLOWED_DEBUG_ADDRESSES.contains(notification.getBody().getModifiedBy())) {
            Blip blip = notification.getBody().getBlip();
            if (isDebugBlip(blip) && notification.getType().equals(EventType.BLIP_SUBMITTED)) {
                AccountManager accountManager = facade.getService(AccountManager.class);
                TemplateRenderer renderer = facade.getService(TemplateRenderer.class);
                blip.getDocument().appendMarkup(renderer.renderTemplate(
                    "Debug.ftl", "user", accountManager.getUser(blip.getCreator())
                ));
            }
        }
    }

    public static boolean isDebugBlip(Blip blip) {
        return blip.getDocument().getText().startsWith("DEBUG");
    }
}
