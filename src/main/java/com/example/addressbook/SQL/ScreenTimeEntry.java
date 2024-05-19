package com.example.addressbook.SQL;

import com.example.addressbook.SQL.User;

import java.time.LocalDateTime;

public class ScreenTimeEntry{
    private int id;
    private User user;

    private int userId;
    private String applicationName;
    private Long duration;
    private LocalDateTime startTime;

    public ScreenTimeEntry(String applicationName, long duration) {
    }


    // Getters
    public int getId(){
        return id;
    }

    public User getUser(){
        return user;
    }

    public int getUserId(){
        return userId;
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

    public ScreenTimeEntry(int userId, String applicationName, long duration, LocalDateTime startTime) {
        this.userId = userId;
        this.applicationName = applicationName;
        this.duration = duration;
        this.startTime = startTime;
    }




}
