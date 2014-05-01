package com.rau.evoting.beans;

import java.io.File;
import java.io.FileWriter;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.rau.evoting.ElGamal.ElGamalHelper;
import com.rau.evoting.data.ElectionDP;
import com.rau.evoting.data.ElectionTrusteeDP;
import com.rau.evoting.data.UserDP;
import com.rau.evoting.models.Election;
import com.rau.evoting.models.Trustee;
import com.rau.evoting.utils.FacebookService;
import com.rau.evoting.utils.MailService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class TrusteeElectionBean {
	private String accessToken;
	
	private int trusteeId;
	private Election election;
	private Trustee trustee;
	
	public TrusteeElectionBean() {	
		trusteeId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("trId").toString());
		System.out.println(trusteeId);
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String code = req.getParameter("code");
		accessToken = FacebookService.getInstance().getAccessToken(code, "TrusteeElection.xhtml");
		trustee = ElectionTrusteeDP.getElectionTrustee(trusteeId);
		election = ElectionDP.getElection(trustee.getElectId());
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User user = fbClient.fetchObject("me", User.class);
		int userId = UserDP.insert(user.getId(), user.getEmail());
		trustee.setEmail(user.getEmail());
		trustee.setUserId(userId);
		ElectionTrusteeDP.updateTrustee(trusteeId, trustee);
	}

	
	public int getTrusteeId() {
		return trusteeId;
	}

	public void setTrusteeId(int trusteeId) {
		this.trusteeId = trusteeId;
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
		ElectionTrusteeDP.setTrusteePublicKey(trustee.getPublicKey(), trusteeId);
		
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
