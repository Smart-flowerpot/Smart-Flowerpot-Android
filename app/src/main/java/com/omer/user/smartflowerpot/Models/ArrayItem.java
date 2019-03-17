package com.omer.user.smartflowerpot.Models;

public class ArrayItem {
    private String optimal_temperature;
    private String optimal_light;
    private String name;
    private String optimal_moisture;
    private String optimal_moisture_soil;

    public String getOptimal_temperature() {
        return optimal_temperature;
    }

    public void setOptimal_temperature(String optimal_temperature) {
        this.optimal_temperature = optimal_temperature;
    }

    public String getOptimal_light() {
        return optimal_light;
    }

    public void setOptimal_light(String optimal_light) {
        this.optimal_light = optimal_light;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptimal_moisture() {
        return optimal_moisture;
    }

    public void setOptimal_moisture(String optimal_moisture) {
        this.optimal_moisture = optimal_moisture;
    }

    public String getOptimal_moisture_soil() {
        return optimal_moisture_soil;
    }

    public void setOptimal_moisture_soil(String optimal_moisture_soil) {
        this.optimal_moisture_soil = optimal_moisture_soil;
    }

    @Override
    public String toString() {
        return "ArrayItem{" +
                "optimal_temperature='" + optimal_temperature + '\'' +
                ", optimal_light='" + optimal_light + '\'' +
                ", name='" + name + '\'' +
                ", optimal_moisture='" + optimal_moisture + '\'' +
                ", optimal_moisture_soil='" + optimal_moisture_soil + '\'' +
                '}';
    }
}
