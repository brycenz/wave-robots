package com.wavenz.robots.ripplie;

import com.google.common.collect.Sets;
import com.wavenz.robots.mvc.FacadeRobotServlet;
import com.wavenz.robots.mvc.LifeCycle;
import com.wavenz.robots.mvc.commands.RegisterAnnotatedComponents;
import com.wavenz.robots.ripplie.mediators.CreateSession;
import com.wavenz.robots.ripplie.mediators.Interactive;
import com.wavenz.robots.ripplie.mediators.Script;
import com.wavenz.robots.ripplie.proxies.RippleManagerImpl;

import javax.servlet.ServletException;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RipplieRobot extends FacadeRobotServlet {
    public RipplieRobot() {
        registerCommand(LifeCycle.INITIALISE, RegisterAnnotatedComponents.class);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        sendNotification(LifeCycle.INITIALISE, Sets.<Class>newHashSet(
            // Model
            RippleManagerImpl.class,

            // View
            CreateSession.class,
            Interactive.class,
            Script.class

            // Controller
        ));
    }
}