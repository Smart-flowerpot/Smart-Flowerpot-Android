package com.omer.user.smartflowerpot.Models;

import java.util.List;

public class AllachievementsItem{
	private String name;
	private List<AchievementlistItem> achievementlist;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAchievementlist(List<AchievementlistItem> achievementlist){
		this.achievementlist = achievementlist;
	}

	public List<AchievementlistItem> getAchievementlist(){
		return achievementlist;
	}

	@Override
 	public String toString(){
		return 
			"AllachievementsItem{" + 
			"name = '" + name + '\'' + 
			",achievementlist = '" + achievementlist + '\'' + 
			"}";
		}
}