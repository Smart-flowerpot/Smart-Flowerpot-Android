package com.omer.user.smartflowerpot.Models;

import java.util.List;

public class Result {
	private List<ArrayItem> array;

	public void setArray(List<ArrayItem> array){
		this.array = array;
	}

	public List<ArrayItem> getArray(){
		return array;
	}

	@Override
 	public String toString(){
		return 
			"Result{" +
			"array = '" + array + '\'' + 
			"}";
		}
}