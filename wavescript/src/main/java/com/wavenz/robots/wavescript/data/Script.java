package com.wavenz.robots.wavescript.data;

import com.google.appengine.api.datastore.Text;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Script {
    @PrimaryKey @Persistent private String name;
    @Persistent private Text content;
    @Persistent private String waveId;
    @Persistent private String waveletId;
    @Persistent private String rootBlipId;

    public Script(String name, String content, String waveId, String waveletId, String rootBlipId) {
        this.name = name;
        this.content = new Text(content);
        this.waveId = waveId;
        this.waveletId = waveletId;
        this.rootBlipId = rootBlipId;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content.getValue();
    }

    public void setContent(String content) {
        this.content = new Text(content);
    }

    public String getWaveId() {
        return waveId;
    }

    public String getWaveletId() {
        return waveletId;
    }

    public String getRootBlipId() {
        return rootBlipId;
    }
}
