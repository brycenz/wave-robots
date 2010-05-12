package com.wavenz.robots.wavefriends.proxies;

import com.wavenz.robots.mvc.annotations.RegisterProxy;
import com.wavenz.robots.mvc.model.AbstractJdoProxy;
import com.wavenz.robots.wavefriends.data.User;
import org.apache.log4j.Logger;

import javax.jdo.PersistenceManager;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterProxy
public class AccountManagerImpl extends AbstractJdoProxy<Object> implements AccountManager {
    private static final Logger LOG = Logger.getLogger(AccountManagerImpl.class);

    public AccountManagerImpl() {
        super(AccountManager.class);
    }

    @Override
    public void createAccount(User user) {
        PersistenceManager pm = getPersistenceManager();
        try {
            pm.makePersistent(user);
        }
        finally {
            pm.close();
        }
    }

    @Override
    public User getUser(String waveAddress) {
        PersistenceManager pm = getPersistenceManager();
        try {
            User user = pm.getObjectById(User.class, waveAddress);
            if (user != null) {
                pm.detachCopy(user);
            }
            return user;
        }
        catch (Exception e) {
            return null;
        }
        finally {
            pm.close();
        }
    }

    @Override
    public boolean isCurrentAccount(String waveAddress) {
        return getUser(waveAddress) != null;
    }
}
