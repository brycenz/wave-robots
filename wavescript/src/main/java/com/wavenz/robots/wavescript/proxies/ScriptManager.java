package com.wavenz.robots.wavescript.proxies;

import com.wavenz.robots.wavescript.data.Script;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface ScriptManager {
    void createScript(Script script);
    void updateScript(Script script);
    Script findScript(String name);
    boolean hasScript(String name);
}
