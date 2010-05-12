package com.wavenz.robots.mvc;

import com.google.common.collect.Sets;
import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.RobotMessageBundle;
import com.wavenz.robots.mvc.common.Notification;
import com.wavenz.robots.mvc.common.NotificationImpl;
import com.wavenz.robots.mvc.common.Notifier;
import com.wavenz.robots.mvc.controller.Command;
import com.wavenz.robots.mvc.controller.Controller;
import com.wavenz.robots.mvc.controller.ControllerImpl;
import com.wavenz.robots.mvc.mediators.WaveletUpdate;
import com.wavenz.robots.mvc.model.Model;
import com.wavenz.robots.mvc.model.ModelImpl;
import com.wavenz.robots.mvc.model.Proxy;
import com.wavenz.robots.mvc.proxies.TemplateRendererImpl;
import com.wavenz.robots.mvc.view.Mediator;
import com.wavenz.robots.mvc.view.Observer;
import com.wavenz.robots.mvc.view.View;
import com.wavenz.robots.mvc.view.ViewImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public abstract class FacadeRobotServlet extends AbstractRobotServlet implements Facade, Notifier<Object> {
    private static final Logger LOG = Logger.getLogger(FacadeRobotServlet.class);

    private Model model;
    private View view;
    private Controller controller;

    protected FacadeRobotServlet() {
        model = new ModelImpl(this);
        view = new ViewImpl(this);
        controller = new ControllerImpl(this);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        sendNotification(LifeCycle.INITIALISE, Sets.<Class>newHashSet(
            // Model
            TemplateRendererImpl.class,

            // View
            WaveletUpdate.class

            // Controller
        ));
    }

    @Override
    public void processEvents(RobotMessageBundle bundle) {
        LOG.debug("Process Events");
        sendNotification(LifeCycle.PROCESS, bundle);
    }

    public void sendNotification(Enum type, Object body) {
        LOG.debug("Send Notification: " + type);
        notifyObservers(new NotificationImpl<Object>(type, body));
    }

    public void addProxy(Proxy proxy) {
        model.addProxy(proxy);
    }

    @Override
    public <T> T getService(Class<T> proxyClass) {
        return model.getService(proxyClass);
    }

    public Proxy getProxy(Class proxyClass) {
        return model.getProxy(proxyClass);
    }

    public void removeProxy(Class proxyClass) {
        model.removeProxy(proxyClass);
    }

    public boolean hasProxy(Class proxyClass) {
        return model.hasProxy(proxyClass);
    }

    public void addMediator(Mediator mediator) {
        view.addMediator(mediator);
    }

    public Mediator getMediator(Class<? extends Mediator> mediatorClass) {
        return view.getMediator(mediatorClass);
    }

    public void removeMediator(Class<? extends Mediator> mediatorClass) {
        view.removeMediator(mediatorClass);
    }

    public boolean hasMediator(Class<? extends Mediator> mediatorClass) {
        return view.hasMediator(mediatorClass);
    }

    public void addObserver(Enum type, Observer observer) {
        view.addObserver(type, observer);
    }

    public void removeObserver(Enum type, Observer observer) {
        view.removeObserver(type, observer);
    }

    public void notifyObservers(Notification notification) {
        view.notifyObservers(notification);
    }

    public void registerCommand(Enum type, Class<? extends Command> commandClass) {
        controller.registerCommand(type, commandClass);
    }

    public void executeCommand(Notification notification) {
        controller.executeCommand(notification);
    }

    public void removeCommand(Enum type) {
        controller.removeCommand(type);
    }

    public boolean hasCommand(Enum type) {
        return controller.hasCommand(type);
    }
}
