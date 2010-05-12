package com.wavenz.robots.ripplie.proxies;

import com.wavenz.robots.mvc.annotations.RegisterProxy;
import com.wavenz.robots.mvc.model.AbstractJdoProxy;
import com.wavenz.robots.ripplie.data.RippleSession;
import com.wavenz.robots.ripplie.util.GAEConnectionManager;
import net.fortytwo.ripple.util.HTTPUtils;
import org.apache.http.conn.ClientConnectionManager;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterProxy
public class RippleManagerImpl extends AbstractJdoProxy<Object> implements RippleManager {
    private static ClientConnectionManager connectionManager = new GAEConnectionManager();

    public RippleManagerImpl() {
        super(RippleManager.class);
        HTTPUtils.setClientConnectionManager(connectionManager);
    }

    @Override
    public RippleSession getSession(String waveId, String waveletId) {
        return new RippleSession();
    }

    @Override
    public RippleSession createSession(String waveId, String waveletId) {
        return new RippleSession();
    }
}
