package com.example.addressbook.ScreenTimeTracking;

import com.example.addressbook.SQL.User;

import java.time.LocalDateTime;

public class ScreenTimeEntry{
    private Long id;
    private User user;
    private String applicationName;
    private Long duration;
    private LocalDateTime startTime;


    // Getters
    public Long getId(){
        return id;
    }

    public User getUser(){
        return user;
    }

    public String getApplicationName(){
        return applicationName;
    }

    public Long getDuration(){
        return duration;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    //setters
    public void setId(){
        this.id = id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setApplicationName(String applicationName){
        this.applicationName = applicationName;
    }

    public void setDuration(long duration){
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }




}
