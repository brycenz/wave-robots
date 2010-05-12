package com.wavenz.robots.wavescript.commands;

import com.wavenz.robots.mvc.annotations.RegisterCommand;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.controller.AbstractCommand;
import com.wavenz.robots.wavescript.NotificationType;
import com.wavenz.robots.wavescript.data.Script;
import com.wavenz.robots.wavescript.proxies.ScriptManager;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterCommand(enumeration = NotificationType.class, value = "SAVE_SCRIPT")
public class SaveScript extends AbstractCommand<Script, Object> {
    @Override
    public void execute(Notification<Script> notification) {
        ScriptManager scriptManager = facade.getService(ScriptManager.class);
        if (scriptManager.hasScript(notification.getBody().getName())) {
            scriptManager.updateScript(notification.getBody());
        }
        else {
            scriptManager.createScript(notification.getBody());
        }
    }
}