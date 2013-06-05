package com.rau.evoting.beans;

import java.io.File;
import java.io.FileWriter;

import javax.mail.MessagingException;





import org.apache.tomcat.util.http.fileupload.FileUtils;

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
	
	public String sendFile() {
		String filename = "D:\\temp.txt";
		File file = new File(filename);
		
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write("Private Key");
			fw.close();
			MailService.sendMessageWithFile("aram.gevorgyan@armsoft.am", "test", "aaaaa a", filename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!file.delete()){
				System.out.println("Error when deleting file");
			}
		}
		
		return "send";
	}

}
