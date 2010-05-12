package com.wavenz.robots.imapwave;

import com.google.wave.api.*;

import javax.mail.*;
import java.util.Properties;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class IMapWave extends AbstractRobotServlet {
    @Override
    public void processEvents(RobotMessageBundle bundle) {
        Wavelet wavelet = bundle.getWavelet();
        Blip rootBlip = wavelet.getRootBlip();
        TextView document = rootBlip.getDocument();

        if (bundle.wasSelfAdded()) {
            if (document.getText().length() != 0) {
                wavelet.appendBlip("To setup your account for I Map Wave please add imapwave@appspot.com to a new wave");
            } else {
                wavelet.setTitle(new StyledText("I Map Wave Configuration", StyleType.HEADING2));
                document.append("\n");
                document.append(
                    "Please fill in the fields below to configure I Map Wave.  Note that the values given " +
                    "are correct for using IMAP connectivity to Gmail.\n\n"
                );

                FormView form = document.getFormView();
                document.appendStyledText(new StyledText("Incoming Mail (IMAP) Server", StyleType.BOLD));
                document.append("\n");
                addFormInputElement(form, "Server Name", AccountConfiguration.IMAP_SERVER_NAME, "imap.gmail.com");
                addFormInputElement(form, "Port", AccountConfiguration.IMAP_PORT, "993");
                addFormCheckElement(form, "SSL", AccountConfiguration.IMAP_SSL, true);
                document.append("\n");

                document.appendStyledText(new StyledText("Outgoing Mail (SMTP) Server", StyleType.BOLD));
                document.append("\n");
                addFormInputElement(form, "Server Name", AccountConfiguration.SMTP_SERVER_NAME, "smtp.gmail.com");
                addFormInputElement(form, "Port", AccountConfiguration.SMTP_PORT, "993");
                addFormCheckElement(form, "SSL", AccountConfiguration.SMTP_SSL, true);
                addFormCheckElement(form, "Authentication", AccountConfiguration.SMTP_AUTHENTICATION, true);
                document.append("\n");

                document.appendStyledText(new StyledText("Account Details", StyleType.BOLD));
                document.append("\n");
                addFormInputElement(form, "Account Name", AccountConfiguration.ACCOUNT_NAME, "");
                addFormInputElement(form, "Email Address", AccountConfiguration.EMAIL_ADDRESS, "");
                addFormInputElement(form, "Password", AccountConfiguration.PASSWORD, "");
                document.append("\n");
                form.append(new FormElement(ElementType.BUTTON, "save", "Save"));
            }
        }

        for (Event event : bundle.getEvents()) {
            switch (event.getType()) {
                case FORM_BUTTON_CLICKED:
                    if (event.getButtonName().equals("save")) {
                        AccountConfiguration accountConfiguration = AccountConfiguration.create(
                            rootBlip.getDocument().getFormView()
                        );
                        accountConfiguration.store(wavelet);

                        Wavelet inboxWavelet = bundle.createWavelet(wavelet.getParticipants());
                        Blip inboxRootBlip = inboxWavelet.getRootBlip();
                        TextView inboxDocument = inboxRootBlip.getDocument();

                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", accountConfiguration.isImapSSL() ? "imaps" : "imap");
                        try {
                            Session session = Session.getDefaultInstance(props, null);
                            Store store = session.getStore("imaps");
                            store.connect(
                                accountConfiguration.getImapServerName(),
                                Integer.parseInt(accountConfiguration.getImapPort()),
                                accountConfiguration.getAccountName(),
                                accountConfiguration.getPassword()
                            );
                            inboxDocument.append(store.toString());
                            inboxDocument.append("\n");

                            Folder inbox = store.getFolder("Inbox");
                            inbox.open(Folder.READ_ONLY);
                            Message messages[] = inbox.getMessages();
                            for (Message message : messages) {
                                inboxDocument.append(message.getSubject());
                                inboxDocument.append("\n");
                            }
                        }
                        catch (NoSuchProviderException e) {
                            throw new RuntimeException(e);
                        }
                        catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
        }
    }

    private void addFormInputElement(FormView form, String label, String fieldName, String value) {
        form.append(new FormElement(ElementType.LABEL, label, label));
        form.append(new FormElement(ElementType.INPUT, fieldName, value));
    }

    private void addFormCheckElement(FormView form, String label, String fieldName, boolean checked) {
        form.append(new FormElement(ElementType.LABEL, label, label));
        form.append(new FormElement(ElementType.CHECK, fieldName, checked ? "true" : "false"));
    }
}
