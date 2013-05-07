package com.rau.evoting.beans;

import java.util.LinkedList;

public class BackNavigationBean {
	
	private LinkedList<String> history;
	
	public BackNavigationBean() {
		history = new LinkedList<String>();
	}
	
	public LinkedList getHistory() {
		return history;
	}
	
	public void setLastPage(String navigationCase) {
		history.push(navigationCase);
		if(history.size() > 10) {
			history.pollLast();
		}
	}
	
	public String getLastPage() {
		return history.pop();
	}

}
