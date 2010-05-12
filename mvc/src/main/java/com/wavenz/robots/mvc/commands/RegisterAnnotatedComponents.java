package com.wavenz.robots.mvc.commands;

import com.wavenz.robots.mvc.controller.CompositeCommand;

import java.util.Set;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class RegisterAnnotatedComponents extends CompositeCommand<Set<Class<?>>,Object> {
    @Override
    protected void initialise() {
        // required to get VFS to work for reflections under google application engine
/*
        if (System.getProperty("os.arch") == null) System.setProperty("os.arch", "");
        if (System.getProperty("os.version") == null) System.setProperty("os.version", "");
        try {
            DefaultFileSystemManager defaultFileSystemManager = (DefaultFileSystemManager) VFS.getManager();
            defaultFileSystemManager.close();
            defaultFileSystemManager.setFilesCache(new DefaultFilesCache());
            defaultFileSystemManager.init();
        } catch (FileSystemException e) {
            throw new RuntimeException(e);
        }
*/

        addCommand(RegisterAnnotatedProxies.class);
        addCommand(RegisterAnnotatedMediators.class);
        addCommand(RegisterAnnotatedCommands.class);
    }
}