package com.wavenz.robots.wavescript.proxies;

import com.wavenz.robots.mvc.annotations.RegisterProxy;
import com.wavenz.robots.mvc.model.AbstractJdoProxy;
import com.wavenz.robots.wavescript.data.Script;
import org.apache.log4j.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterProxy
public class ScriptManagerImpl extends AbstractJdoProxy<Object> implements ScriptManager {
    private static final Logger LOG = Logger.getLogger(ScriptManagerImpl.class);
    public ScriptManagerImpl() {
        super(ScriptManager.class);
    }

    @Override
    public void createScript(Script script) {
        PersistenceManager pm = getPersistenceManager();
        try {
            pm.makePersistent(script);
        }
        finally {
            pm.close();
        }
    }

    @Override
    public void updateScript(Script script) {
        PersistenceManager pm = getPersistenceManager();
        try {
            Script toUpdate = pm.getObjectById(Script.class, script.getName());
            toUpdate.setContent(script.getContent());
            pm.makePersistent(script);
        }
        finally {
            pm.close();
        }
    }

    @Override
    public Script findScript(String name) {
        PersistenceManager pm = getPersistenceManager();
        try {
            Script script = pm.getObjectById(Script.class, name);
            if (script != null) {
                LOG.debug(script.getContent());
                pm.detachCopy(script);
            }
            return script;
        }
        catch (Exception e) {
            return null;
        }
        finally {
            pm.close();
        }
    }

    @Override
    public boolean hasScript(String name) {
        return findScript(name) != null;
    }
}
