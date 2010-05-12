package com.wavenz.robots.mvc.proxies;

import com.google.common.collect.Maps;
import com.wavenz.robots.mvc.annotations.RegisterProxy;
import com.wavenz.robots.mvc.model.AbstractJdoProxy;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
@RegisterProxy
public class TemplateRendererImpl extends AbstractJdoProxy<Object> implements TemplateRenderer {
    private Configuration configuration;

    public TemplateRendererImpl() {
        super(TemplateRenderer.class);
        configuration = new Configuration();
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/templates"));
    }

    @Override
    public String renderTemplate(String name) {
        return renderTemplate(name, Maps.<String, Object>newHashMap());
    }

    @Override
    public String renderTemplate(String name, String parameter, Object value) {
        HashMap<String,Object> context = Maps.<String, Object>newHashMap();
        context.put(parameter, value);
        return renderTemplate(name, context);
    }

    @Override
    public String renderTemplate(String name, Map<String, Object> context) {
        try {
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate(name);
            template.process(context, stringWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}