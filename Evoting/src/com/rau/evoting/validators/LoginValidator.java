package com.rau.evoting.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.User;

public class LoginValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String username = (String)value; 
		UIInput passwordInput = (UIInput)component.getAttributes().get("passwordComponent");
		String password = (String) passwordInput.getSubmittedValue();
		
		SqlDataProvider dataprovider = SqlDataProvider.getInstance(); 
		User user = dataprovider.getUser(username);
		if(user == null){
			throw new ValidatorException(new FacesMessage("no such user"));
		}
		
		if (!user.getPassword().equals(password)) {
			throw new ValidatorException(new FacesMessage(
					"incorrect password"));
		}
		
		return ;
	}

}
