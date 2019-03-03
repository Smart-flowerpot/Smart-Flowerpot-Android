package com.omer.user.smartflowerpot.Models;

public class ArrayItem{
	private String optimalTemperature;
	private String optimalLight;
	private String name;
	private String optimalMoisture;

	public void setOptimalTemperature(String optimalTemperature){
		this.optimalTemperature = optimalTemperature;
	}

	public String getOptimalTemperature(){
		return optimalTemperature;
	}

	public void setOptimalLight(String optimalLight){
		this.optimalLight = optimalLight;
	}

	public String getOptimalLight(){
		return optimalLight;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOptimalMoisture(String optimalMoisture){
		this.optimalMoisture = optimalMoisture;
	}

	public String getOptimalMoisture(){
		return optimalMoisture;
	}

	@Override
 	public String toString(){
		return 
			"ArrayItem{" + 
			"optimal_temperature = '" + optimalTemperature + '\'' + 
			",optimal_light = '" + optimalLight + '\'' + 
			",name = '" + name + '\'' + 
			",optimal_moisture = '" + optimalMoisture + '\'' + 
			"}";
		}
}
