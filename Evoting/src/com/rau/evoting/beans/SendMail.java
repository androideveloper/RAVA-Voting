package com.rau.evoting.beans;

import javax.mail.MessagingException;

import com.rau.evoting.utils.MailService;

public class SendMail {
	
	private String email;
	private String message;

	public SendMail() {
			message = "Hi Mr ... !!!!";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public String send(){
		try {
			for (int i = 1; i < 6; ++i) {
				MailService.sendMessage(email, "Test", message + i);
			}
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		return "send";
	}

}
