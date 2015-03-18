package com.sevenheroes.bean;

/**
 * Created by forest on 2015/3/15.
 */
public class Role {
    private String name ;
    private String level ;
    private String experience ;
    private String state;
    private String job;
    private int healthAmount;
    private String takeSoldierType;
    private int takeSoldierAmount;
    private boolean isBeingSelected ;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    public String getTakeSoldierType() {
        return takeSoldierType;
    }

    public void setTakeSoldierType(String takeSoldierType) {
        this.takeSoldierType = takeSoldierType;
    }

    public int getTakeSoldierAmount() {
        return takeSoldierAmount;
    }

    public void setTakeSoldierAmount(int takeSoldierAmount) {
        this.takeSoldierAmount = takeSoldierAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public boolean isBeingSelected() {
        return isBeingSelected;
    }

    public void setBeingSelected(boolean isBeingSelected) {
        this.isBeingSelected = isBeingSelected;
    }

}
