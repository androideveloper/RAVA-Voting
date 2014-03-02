package com.rau.evoting.beans;

import java.io.Console;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.StreamedContent;

import com.rau.evoting.data.SqlDataProvider;
import com.rau.evoting.models.Answer;
import com.rau.evoting.models.Election;
import com.rau.evoting.utils.BarcodeHelper;
import com.rau.evoting.utils.Util;

public class Vote {
	
	private ArrayList<Answer> answers;
	private ArrayList<Integer> a1;
	private ArrayList<Integer> a2;
	private boolean showEncode;
	private boolean showShuffle;
	private StreamedContent encoded1;
	private StreamedContent encoded2;
	private String selectedDecodedList;
	private boolean showDecode;
	private boolean showDecoded1;
	private boolean showDecoded2;
	private int selectedVote;
		
	private String publicKey;
	
	public Vote() {
		
	}
	
	public String fromElection() {
		int elId = Integer.valueOf(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("elId"));
		answers = SqlDataProvider.getInstance().getElectionAnswers(elId);
		showEncode = false;
		showShuffle = true;
		selectedDecodedList = "1";
		showDecode = false;
		showDecoded1 = false;
		showDecoded2 = false;
		selectedVote = 1;
		encoded1 = null;
		encoded2 = null;
		a1 = new ArrayList<Integer>();
		a2 = new ArrayList<Integer>();
		for(Answer ans : answers) {
			a1.add(ans.getId());
			a2.add(ans.getId());
		}
		publicKey = SqlDataProvider.getInstance().getElection(elId).getPublicKey();
		//System.out.print("public_key: " + el.getPublicKey());
		return "Vote";
	}
	
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public ArrayList<Integer> getA1() {
		return a1;
	}

	public void setA1(ArrayList<Integer> a1) {
		this.a1 = a1;
	}

	public ArrayList<Integer> getA2() {
		return a2;
	}

	public void setA2(ArrayList<Integer> a2) {
		this.a2 = a2;
	}
	
	public boolean isShowEncode() {
		return showEncode;
	}

	public void setShowEncode(boolean showEncode) {
		this.showEncode = showEncode;
	}

	public boolean isShowShuffle() {
		return showShuffle;
	}

	public void setShowShuffle(boolean showShuffle) {
		this.showShuffle = showShuffle;
	}

	public StreamedContent getEncoded1() {
		return encoded1;
	}

	public void setEncoded1(StreamedContent encoded1) {
		this.encoded1 = encoded1;
	}

	public StreamedContent getEncoded2() {
		return encoded2;
	}

	public void setEncoded2(StreamedContent encoded2) {
		this.encoded2 = encoded2;
	}
	
	public String getSelectedDecodedList() {
		return selectedDecodedList;
	}

	public void setSelectedDecodedList(String selectedDecodedList) {
		this.selectedDecodedList = selectedDecodedList;
	}
	
	public boolean isShowDecode() {
		return showDecode;
	}

	public void setShowDecode(boolean showDecode) {
		this.showDecode = showDecode;
	}

	public boolean isShowDecoded1() {
		return showDecoded1;
	}

	public void setShowDecoded1(boolean showDecoded1) {
		this.showDecoded1 = showDecoded1;
	}

	public boolean isShowDecoded2() {
		return showDecoded2;
	}

	public void setShowDecoded2(boolean showDecoded2) {
		this.showDecoded2 = showDecoded2;
	}

	public int getSelectedVote() {
		return selectedVote;
	}

	public void setSelectedVote(int selectedVote) {
		this.selectedVote = selectedVote;
	}

	public void shuffle(AjaxBehaviorEvent event) {
		Util.shuffle(a1);
		Util.shuffle(a2);
		showEncode = true;
	}
	
	public void encode(AjaxBehaviorEvent event) {
		showShuffle = false;
		System.out.print("public_key: " + publicKey);
		encoded1 = BarcodeHelper.getEncodedBarcodeFromIntList(a1, publicKey);
		encoded2 = BarcodeHelper.getEncodedBarcodeFromIntList(a2, publicKey);
		showEncode = false;
		showDecode = true;
	}
	
	public String decode() {
		showDecode = false;
		if(selectedDecodedList.equals("1")){
			showDecoded1 = true;
			//make decode logic
		} else {
			showDecoded2 = true;
			//make decode logic
		}
		return null;
	}
	
	public String vote() {
		// vote
		return null;
	}

}
