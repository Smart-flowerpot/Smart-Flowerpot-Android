package com.omer.user.smartflowerpot.Models;

public class Plant {

    private int plant_id;
    private String name;
    private String type;
    private int light;
    private int temperature;
    private int moisture_air;
    private int moisture_soil;
    private int frostbite;
    private int water;

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMoisture_air() {
        return moisture_air;
    }

    public void setMoisture_air(int moisture_air) {
        this.moisture_air = moisture_air;
    }

    public int getMoisture_soil() {
        return moisture_soil;
    }

    public void setMoisture_soil(int moisture_soil) {
        this.moisture_soil = moisture_soil;
    }

    public int getFrostbite() {
        return frostbite;
    }

    public void setFrostbite(int frostbite) {
        this.frostbite = frostbite;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}
