package com.rau.evoting.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.rau.evoting.listeners.MyTransportListener;



public class MailService {

	private static final String HOST = "smtp.gmail.com";
	private static final int PORT = 465;
	private static final String USER = "rava.voting";
	private static final String PASSWORD = "rau#rava#voting";
	private static final String FROM = "rava.voting@gmail.com";
	
	private static Session mailSession;
	private static MailService mailService = null;

	private MailService() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", HOST);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.from", FROM);
		props.put("mail.smtps.quitwait", "false");

		mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
	}

	public static void sendMessage(String recipient, String subject,
			String message) throws MessagingException {

		if (mailService == null) {
			mailService = new MailService();
		}

		MimeMessage mimeMessage = new MimeMessage(mailSession);

		mimeMessage.setFrom(new InternetAddress(FROM));
		mimeMessage.setSender(new InternetAddress(FROM));
		mimeMessage.setSubject(subject);
		mimeMessage.setContent(message, "text/plain");

		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		Transport transport = mailSession.getTransport("smtps");
		transport.connect(HOST, PORT, USER, PASSWORD);
		
		transport.addTransportListener(new MyTransportListener());

		transport.sendMessage(mimeMessage,
				mimeMessage.getRecipients(Message.RecipientType.TO));
		transport.close();

	}
}
