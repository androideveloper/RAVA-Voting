package com.rau.evoting.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;

import com.rau.evoting.utils.MailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCandidateValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String id = component.getId();
		if(id.equals("dob")) {
			Date dob = (Date)value;
			SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
			Date beforeDate = null;
			Date afterDate = null;
			try {
				beforeDate = format.parse("01-01-1753");
				afterDate = format.parse("31-12-9999");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dob.before(beforeDate)){
				throw new ValidatorException(new FacesMessage("Date must be not less than 01-01-1753"));
			}
			if(dob.after(afterDate)){
				throw new ValidatorException(new FacesMessage("Date must be not great than 31-12-9999"));
			}
		} else if (id.equals("email")) {
			UIInput nameInput = (UIInput)component.getAttributes().get("nameComponent");
			String name = (String)nameInput.getValue();
			UIInput surnameInput = (UIInput)component.getAttributes().get("surnameComponent");
			String surname = (String)nameInput.getValue();
			String message = "Hi Mr. " + name + " "  + surname;
			/*System.out.println("Message: " + message);
			try {
				MailService.sendMessage((String)value, "YAHOOOOOOOOOOOOOOOOOO", message);
			} catch (MessagingException e) {
				e.printStackTrace();
				throw new ValidatorException(new FacesMessage("invalid email address"));
			}*/
			if(! validateEmail((String)value)) {
				throw new ValidatorException(new FacesMessage("invalid email address"));
			}
		}
		
	}
	
	private boolean validateEmail(String email) {
		boolean isValid = false;
		InternetAddress address;
		try {
			address = new InternetAddress(email);
			address.validate();
			isValid = true;
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return isValid;
	}

}
