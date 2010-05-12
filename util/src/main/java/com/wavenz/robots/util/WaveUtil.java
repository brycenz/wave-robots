package com.wavenz.robots.util;

import com.google.wave.api.Blip;
import com.google.wave.api.Wavelet;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class WaveUtil {
    public static boolean isNewWave(Wavelet wavelet) {
        return !wavelet.getRootBlip().hasChildren() &&
            wavelet.getParticipants().size() == 2 &&
            wavelet.getRootBlip().getDocument().getText().length() == 0;
    }

    public static boolean isRootBlip(Blip blip) {
        return blip.getWavelet().getRootBlipId().equals(blip.getBlipId());
    }
}
