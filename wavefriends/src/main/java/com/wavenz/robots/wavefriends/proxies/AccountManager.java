package com.wavenz.robots.wavefriends.proxies;

import com.wavenz.robots.wavefriends.data.User;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface AccountManager {
    void createAccount(User user);
    User getUser(String waveAddress);
    boolean isCurrentAccount(String waveAddress);
}
