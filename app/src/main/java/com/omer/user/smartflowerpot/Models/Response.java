package com.omer.user.smartflowerpot.Models;

import java.util.List;

public class Response{
	private List<AllachievementsItem> allachievements;

	public void setAllachievements(List<AllachievementsItem> allachievements){
		this.allachievements = allachievements;
	}

	public List<AllachievementsItem> getAllachievements(){
		return allachievements;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"allachievements = '" + allachievements + '\'' + 
			"}";
		}
}