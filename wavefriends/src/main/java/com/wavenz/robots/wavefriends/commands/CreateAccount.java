package com.wavenz.robots.wavefriends.commands;

import com.wavenz.robots.mvc.annotations.RegisterCommand;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.controller.AbstractCommand;
import com.wavenz.robots.mvc.model.Proxy;
import com.wavenz.robots.wavefriends.NotificationType;
import com.wavenz.robots.wavefriends.data.User;
import com.wavenz.robots.wavefriends.proxies.AccountManager;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterCommand(enumeration = NotificationType.class, value = "CREATE_ACCOUNT")
public class CreateAccount extends AbstractCommand<User, Object> {
    @Override
    public void execute(Notification<User> notification) {
        AccountManager accountManager = facade.getService(AccountManager.class);
        accountManager.createAccount(notification.getBody());
    }
}
