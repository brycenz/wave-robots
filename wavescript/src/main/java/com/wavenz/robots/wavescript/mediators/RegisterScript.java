package com.wavenz.robots.wavescript.mediators;

import com.google.common.collect.Lists;
import com.google.wave.api.*;
import com.wavenz.robots.mvc.annotations.RegisterMediator;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.view.AbstractMediator;
import com.wavenz.robots.wavescript.NotificationType;
import com.wavenz.robots.wavescript.WaveScriptRobot;
import com.wavenz.robots.wavescript.data.Script;
import com.wavenz.robots.wavescript.proxies.ScriptManager;
import org.apache.log4j.Logger;
import org.mozilla.javascript.*;
import org.mozilla.javascript.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterMediator
public class RegisterScript extends AbstractMediator<Event, Script> {
    private static final Logger LOG = Logger.getLogger(RegisterScript.class);
    private static final Pattern SCRIPT_PATTERN = Pattern.compile(".*#!wavescript\\.(\\w*) = <<<(.*?)>>>.*", Pattern.DOTALL);

    @Override
    public List<? extends Enum> getNotificationInterests() {
        return Lists.newArrayList(EventType.WAVELET_SELF_ADDED, EventType.BLIP_SUBMITTED);
    }

    @Override
    public void handleNotification(Notification<Event> notification) {
        Wavelet wavelet = notification.getBody().getWavelet();
        Blip blip = notification.getBody().getBlip();
        if (wavelet.getParticipants().contains(WaveScriptRobot.WAVE_SCRIPT_ADDRESS) && blip.getBlipId().equals(wavelet.getRootBlipId())) {
            LOG.debug(MessageFormat.format(
                "Script Registration (waveId:{0}, waveletId:{1}, blipId:{2}",
                wavelet.getWaveId(), wavelet.getWaveletId(), blip.getBlipId()
            ));
            TextView document = blip.getDocument();
            Matcher matcher = SCRIPT_PATTERN.matcher(document.getText());
            if (matcher.matches()) {
                String scriptName = matcher.group(1);
                String scriptContent = matcher.group(2);

                if (isScriptNameAvailable(blip, scriptName)) {
                    if (isValidScript(blip, scriptName, scriptContent)) {
                        sendNotification(
                            NotificationType.SAVE_SCRIPT,
                            new Script(
                                scriptName,
                                scriptContent,
                                wavelet.getWaveId(),
                                wavelet.getWaveletId(),
                                wavelet.getRootBlipId()
                            )
                        );

                        String address = "wavescript+" + scriptName + "@appspot.com";
                        if (!wavelet.getParticipants().contains(address)) {
                            wavelet.addParticipant(address);
                        }
                    }
                }
                else {
                    blip.createChild().getDocument().append("\nThe script name: " + scriptName + " is already in use");
                }
            }
        }
    }

    private boolean isScriptNameAvailable(Blip blip, String scriptName) {
        ScriptManager manager = facade.getService(ScriptManager.class);
        Script script = manager.findScript(scriptName);
        if (script != null) {
            if (!script.getWaveId().equals(blip.getWavelet().getWaveId())) return false;
            if (!script.getWaveletId().equals(blip.getWavelet().getWaveletId())) return false;
            if (!script.getRootBlipId().equals(blip.getBlipId())) return false;
        }
        return true;
    }

    private boolean isValidScript(Blip blip, String scriptName, String scriptContent) {
        try {
            final Context rhinoContext = ContextFactory.getGlobal().enterContext();
            Scriptable scope = rhinoContext.initStandardObjects();
            rhinoContext.evaluateReader(scope, new StringReader(scriptContent), scriptName, 1, null);

            Object processEvents = scope.get("processEvents", scope);
            if (!(processEvents instanceof Function)) {
                TextView document = blip.createChild().getDocument();
                document.append("\nNo function named processEvents within script");
                return false;
            }
        }
        catch (RhinoException e) {
            String line = null;
            BufferedReader reader = new BufferedReader(new StringReader(scriptContent));
            for (int i = 0; i < e.lineNumber(); i++) {
                try {
                    line = reader.readLine();
                } catch (IOException ignored) {}
            }
            TextView document = blip.createChild().getDocument();
            document.append("\n" + e.getMessage() + " [" + line + "]");
            return false;
        }
        catch (Exception e) {
            LOG.warn(e);
            TextView document = blip.createChild().getDocument();
            document.append("\nUnknown exception occured processing script");
            return false;
        }
        finally {
            Context.exit();
        }
        return true;
    }
}