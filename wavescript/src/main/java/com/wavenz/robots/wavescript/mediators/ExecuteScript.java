package com.wavenz.robots.wavescript.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.Blip;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.TextView;
import com.google.wave.api.Wavelet;
import com.wavenz.robots.mvc.LifeCycle;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavescript.WaveScriptRobot;
import com.wavenz.robots.wavescript.data.Script;
import com.wavenz.robots.wavescript.proxies.ScriptManager;
import org.apache.log4j.Logger;
import org.mozilla.javascript.*;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class ExecuteScript extends AbstractMediator<RobotMessageBundle, Object> {
    private static final Logger LOG = Logger.getLogger(ExecuteScript.class);
    private static final Pattern ADDRESS_NAME_PATTERN = Pattern.compile("wavescript\\+(.*)@appspot.com");

    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(LifeCycle.PROCESS);
    }

    @Override
    public void handleNotification(Notification<RobotMessageBundle> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        List<Script> matchedScripts = getMatchedScripts(wavelet.getParticipants());
        for (Script script : matchedScripts) {
            if (!isDefinitionWavelet(script, wavelet)) {
                executeScript(script, notification.getBody());
            }
        }
    }

    private boolean isDefinitionWavelet(Script script, Wavelet wavelet) {
        return script.getWaveId().equals(wavelet.getWaveId()) && script.getWaveletId().equals(wavelet.getWaveletId());
    }

    private void executeScript(Script script, RobotMessageBundle bundle) {
        try {
            final Context rhinoContext = ContextFactory.getGlobal().enterContext();
            Scriptable scope = rhinoContext.initStandardObjects();
            rhinoContext.evaluateReader(scope, new StringReader(script.getContent()), script.getName(), 0, null);

            Object processEvents = scope.get("processEvents", scope);
            if (!(processEvents instanceof Function)) {
                throw new RuntimeException("No function processEvents");
            } else {
                Object wrappedBundle = Context.javaToJS(bundle, scope);
                Object arguments[] = { wrappedBundle };
                Function processEventsFunction = (Function) processEvents;
                processEventsFunction.call(rhinoContext, scope, scope, arguments);
            }
        }
        catch (RhinoException e) {
            Blip blip = bundle.getBlip(script.getWaveId(), script.getWaveletId(), script.getRootBlipId());
            TextView document = blip.createChild().getDocument();
            String line = null;
            BufferedReader reader = new BufferedReader(new StringReader(script.getContent()));
            for (int i = 0; i < e.lineNumber(); i++) {
                try {
                    line = reader.readLine();
                } catch (IOException ignored) {}
            }
            document.append("\n" + e.getMessage() + ": " + line);
        }
        catch (Throwable e) {
            LOG.error(e);
        }
        finally {
            Context.exit();
        }
    }

    private List<Script> getMatchedScripts(List<String> participants) {
        List<Script> scripts = Lists.newArrayList();
        for (String participant : participants) {
            Matcher matcher = ADDRESS_NAME_PATTERN.matcher(participant);
            if (matcher.matches()) {
                ScriptManager scriptManager = facade.getService(ScriptManager.class);
                Script script = scriptManager.findScript(matcher.group(1));
                if (script != null) {
                    scripts.add(script);
                }
            }
        }
        return scripts;
    }
}