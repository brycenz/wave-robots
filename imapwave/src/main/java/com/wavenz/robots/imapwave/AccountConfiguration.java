package com.wavenz.robots.imapwave;

import com.google.gson.Gson;
import com.google.wave.api.*;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public class AccountConfiguration {
    public static final String DATA_DOCUMENT = "ACCOUNT_CONFIGURATION";

    public static final String IMAP_SERVER_NAME = "imapServerName";
    public static final String IMAP_PORT = "imapPort";
    public static final String IMAP_SSL = "imapSSL";
    public static final String SMTP_SERVER_NAME = "smtpServerName";
    public static final String SMTP_PORT = "smtpPort";
    public static final String SMTP_SSL = "smtpSSL";
    public static final String SMTP_AUTHENTICATION = "smtpAuthentication";
    public static final String ACCOUNT_NAME = "accountName";
    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String PASSWORD = "password";

    private String imapServerName;
    private String imapPort;
    private Boolean imapSSL;

    private String smtpServerName;
    private String smtpPort;
    private Boolean smtpSSL;
    private Boolean smtpAuthentication;

    private String accountName;
    private String emailAddress;
    private String password;

    public String getImapServerName() {
        return imapServerName;
    }

    public String getImapPort() {
        return imapPort;
    }

    public Boolean isImapSSL() {
        return imapSSL;
    }

    public String getSmtpServerName() {
        return smtpServerName;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public Boolean isSmtpSSL() {
        return smtpSSL;
    }

    public Boolean isSmtpAuthentication() {
        return smtpAuthentication;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void store(Wavelet wavelet) {
        wavelet.setDataDocument(DATA_DOCUMENT, new Gson().toJson(this));
    }

    public void appendToBlip(Blip blip) {
        TextView document = blip.getDocument();
        document.appendStyledText(new StyledText("Incoming Mail (IMAP) Server", StyleType.BOLD));
        document.append("\n");
        document.append("Server Name: " + imapServerName + "\n");
        document.append("Port: " + imapPort + "\n");
        document.append("SSL: " + imapSSL + "\n");

        document.appendStyledText(new StyledText("Outgoing Mail (SMTP) Server", StyleType.BOLD));
        document.append("\n");
        document.append("Server Name: " + smtpServerName + "\n");
        document.append("Port: " + smtpPort + "\n");
        document.append("SSL: " + smtpSSL + "\n");
        document.append("Authentication: " + smtpAuthentication + "\n");

        document.appendStyledText(new StyledText("Account Details", StyleType.BOLD));
        document.append("\n");
        document.append("Account Name: " + accountName + "\n");
        document.append("Email Address: " + emailAddress + "\n");
    }

    public static AccountConfiguration create(FormView form) {
        AccountConfiguration configuration =  new AccountConfiguration();

        configuration.imapServerName = getStringValue(form, IMAP_SERVER_NAME);
        configuration.imapPort = getStringValue(form, IMAP_PORT);
        configuration.imapSSL = getBooleanValue(form, IMAP_SSL);

        configuration.smtpServerName = getStringValue(form, SMTP_SERVER_NAME);
        configuration.smtpPort = getStringValue(form, SMTP_PORT);
        configuration.smtpSSL = getBooleanValue(form, SMTP_SSL);
        configuration.smtpAuthentication = getBooleanValue(form, SMTP_AUTHENTICATION);

        configuration.accountName = getStringValue(form, ACCOUNT_NAME);
        configuration.emailAddress = getStringValue(form, EMAIL_ADDRESS);
        configuration.password = getStringValue(form, PASSWORD);

        return configuration;
    }

    private static String getStringValue(FormView form, String fieldName) {
        FormElement element = form.getFormElement(fieldName);
        return element != null ? element.getValue() : null;
    }

    private static Boolean getBooleanValue(FormView form, String fieldName) {
        FormElement element = form.getFormElement(fieldName);
        return element == null || "true".equals(element.getValue());
    }

    public static AccountConfiguration load(Wavelet wavelet) {
        return new Gson().fromJson(wavelet.getDataDocument(DATA_DOCUMENT), AccountConfiguration.class);
    }
}
