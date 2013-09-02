package com.rau.evoting.beans;

import java.io.File;
import java.io.FileWriter;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;
import com.rau.evoting.utils.ElGamalHelper;
import com.rau.evoting.utils.FacebookService;
import com.rau.evoting.utils.MailService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class TrusteeElectionBean {
	private String accessToken;
	
	private int tempTrId;
	private Election election;
	private Trustee trustee;
	
	public TrusteeElectionBean() {
		String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name"); 
		System.out.println(name);
		FacesContext context = FacesContext.getCurrentInstance();
		tempTrId = (int)context.getApplication().evaluateExpressionGet(context, "#{trhome.tempTrId}", int.class);
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String code = req.getParameter("code");
		accessToken = FacebookService.getInstance().getAccessToken(code, "TrusteeElection.xhtml");
		//int elId = SqlDataProvider.getInstance().getTrusteeElectionId(tempTrId);
		//election = SqlDataProvider.getInstance().getElection(elId);
		//election = SqlDataProvider.getInstance().getTrusteeElection(tempTrId);
		trustee = SqlDataProvider.getInstance().getElectionTrustee(tempTrId);
		election = SqlDataProvider.getInstance().getElection(trustee.getElectId());
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User user = fbClient.fetchObject("me", User.class);
		//Trustee tr = new Trustee(user.getId(), user.getEmail(), false);
		trustee.setEmail(user.getEmail());
		trustee.setId(user.getId());
		SqlDataProvider.getInstance().updateTrustee(tempTrId, trustee);
		//SqlDataProvider.getInstance().insertTrustee(election.getId(), tr);
		//SqlDataProvider.getInstance().deleteTempTrustee(tempTrId);
	}

	public int getTempTrId() {
		return tempTrId;
	}

	public void setTempTrId(int tempTrId) {
		this.tempTrId = tempTrId;
	}

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}
		
	public Trustee getTrustee() {
		return trustee;
	}

	public void setTrustee(Trustee trustee) {
		this.trustee = trustee;
	}

	public String generateKey() {
		String filename = "D:\\PrivateKey.txt";
		File file = new File(filename);
		ElGamalHelper elHelper = new ElGamalHelper();
		trustee.setPublicKey(elHelper.getPublicKey());
		trustee.setGenerated(true);
		SqlDataProvider.getInstance().setTrusteePublicKey(trustee.getPublicKey(), tempTrId);
		
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(elHelper.getPrivateKey());
			fw.close();
			MailService.sendMessageWithFile(trustee.getEmail(), "Private Key", "This is your private key for " + election.getName() + " evoting", filename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!file.delete()){
				System.out.println("Error when deleting file");
			}
		}
		
		return "";
	}
}
