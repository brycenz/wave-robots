package com.wavenz.robots.wavefriends;

import com.google.common.collect.Sets;
import com.wavenz.robots.mvc.FacadeRobotServlet;
import com.wavenz.robots.mvc.LifeCycle;
import com.wavenz.robots.mvc.commands.RegisterAnnotatedComponents;
import com.wavenz.robots.wavefriends.commands.CreateAccount;
import com.wavenz.robots.wavefriends.mediators.Account;
import com.wavenz.robots.wavefriends.mediators.Debug;
import com.wavenz.robots.wavefriends.mediators.SignUp;
import com.wavenz.robots.wavefriends.mediators.Usage;
import com.wavenz.robots.wavefriends.proxies.AccountManagerImpl;

import javax.servlet.ServletException;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class WaveFriendsRobot extends FacadeRobotServlet {
    public WaveFriendsRobot() {
        registerCommand(LifeCycle.INITIALISE, RegisterAnnotatedComponents.class);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        sendNotification(LifeCycle.INITIALISE, Sets.<Class>newHashSet(
            // Model
            AccountManagerImpl.class,

            // View
            Account.class,
            Debug.class,
            SignUp.class,
            Usage.class,

            // Controller
            CreateAccount.class
        ));
    }
}
