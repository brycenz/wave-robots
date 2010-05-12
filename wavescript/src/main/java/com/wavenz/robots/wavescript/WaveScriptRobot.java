package com.wavenz.robots.wavescript;

import com.google.common.collect.Sets;
import com.wavenz.robots.mvc.FacadeRobotServlet;
import com.wavenz.robots.mvc.LifeCycle;
import com.wavenz.robots.mvc.commands.RegisterAnnotatedComponents;
import com.wavenz.robots.wavescript.commands.SaveScript;
import com.wavenz.robots.wavescript.mediators.ExecuteScript;
import com.wavenz.robots.wavescript.mediators.RegisterScript;
import com.wavenz.robots.wavescript.proxies.ScriptManagerImpl;

import javax.servlet.ServletException;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class WaveScriptRobot extends FacadeRobotServlet {
    public static final String WAVE_SCRIPT_ADDRESS = "wavescript@appspot.com";

    public WaveScriptRobot() {
        registerCommand(LifeCycle.INITIALISE, RegisterAnnotatedComponents.class);
    }


    @Override
    public void init() throws ServletException {
        super.init();
        sendNotification(LifeCycle.INITIALISE, Sets.<Class>newHashSet(
            // Model
            ScriptManagerImpl.class,

            // View
            ExecuteScript.class, RegisterScript.class,

            // Controller
            SaveScript.class
        ));
    }
}
