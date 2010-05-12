package com.wavenz.robots.wavefriends.data;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User {
    @PrimaryKey
    @Persistent
    private String waveAddress;

    @Persistent
    private String firstName;

    @Persistent
    private String lastName;

    public User(String waveAddress, String firstName, String lastName) {
        this.waveAddress = waveAddress;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getWaveAddress() {
        return waveAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
