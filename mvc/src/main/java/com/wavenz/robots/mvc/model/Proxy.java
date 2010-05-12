package com.wavenz.robots.mvc.model;

import com.wavenz.robots.mvc.common.Registerable;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface Proxy extends Registerable {
    Class getProxyClass();
}
