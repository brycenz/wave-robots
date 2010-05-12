package com.wavenz.robots.ripplie.proxies;

import com.wavenz.robots.ripplie.data.RippleSession;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface RippleManager {
    RippleSession getSession(String waveId, String waveletId);
    RippleSession createSession(String waveId, String waveletId);
}
