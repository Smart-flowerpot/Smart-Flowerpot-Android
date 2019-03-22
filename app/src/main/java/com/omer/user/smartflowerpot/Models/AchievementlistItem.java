package com.omer.user.smartflowerpot.Models;

public class AchievementlistItem{
	private String achievementname;
	private String points;
	private String status;

	public AchievementlistItem(String achievementname, String points, String status) {
		this.achievementname = achievementname;
		this.points = points;
		this.status = status;
	}

	public void setAchievementname(String achievementname){
		this.achievementname = achievementname;
	}

	public String getAchievementname(){
		return achievementname;
	}

	public void setPoints(String points){
		this.points = points;
	}

	public String getPoints(){
		return points;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AchievementlistItem{" + 
			"achievementname = '" + achievementname + '\'' + 
			",points = '" + points + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
