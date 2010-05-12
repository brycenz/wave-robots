package com.wavenz.robots.mvc.proxies;

import java.util.Map;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public interface TemplateRenderer {
    String renderTemplate(String name);
    String renderTemplate(String name, String parameter, Object value);
    String renderTemplate(String name, Map<String, Object> context);
}
