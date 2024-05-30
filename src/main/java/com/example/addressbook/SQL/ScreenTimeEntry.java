package com.example.addressbook.SQL;

import com.example.addressbook.SQL.User;

import java.time.LocalDateTime;
/**
 * This class is used to handle the screen time entry
 */
public class ScreenTimeEntry{
    private int id;
    private User user;

    private int userId;
    private String applicationName;
    private Long duration;
    private LocalDateTime startTime;

    /**
     * Constructor for the ScreenTimeEntry
     * @param applicationName The application name
     * @param duration The duration
     */
    public ScreenTimeEntry(String applicationName, long duration) {
    }


    // Getters
    /**
     * Get the id of the screen time entry
     * @return The id of the screen time entry
     */
    public int getId(){
        return id;
    }
    /**
     * Get the user of the screen time entry
     * @return The user of the screen time entry
     */
    public User getUser(){
        return user;
    }
    /**
     * Get the user id of the screen time entry
     * @return The user id of the screen time entry
     */
    public int getUserId(){
        return userId;
    }
    /**
     * Get the application name of the screen time entry
     * @return The application name of the screen time entry
     */
    public String getApplicationName(){
        return applicationName;
    }
    /**
     * Get the duration of the screen time entry
     * @return The duration of the screen time entry
     */
    public Long getDuration(){
        return duration;
    }
    /**
     * Get the start time of the screen time entry
     * @return The start time of the screen time entry
     */
    public LocalDateTime getStartTime(){
        return startTime;
    }

    //setters
    /**
     * Set the id of the screen time entry
     */
    public void setId(){
        this.id = id;
    }
    /**
     * Set the user of the screen time entry
     * @param user The user of the screen time entry
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Set the application name of the screen time entry
     * @param applicationName The application name of the screen time entry
     */
    public void setApplicationName(String applicationName){
        this.applicationName = applicationName;
    }

    /**
     * Set the duration of the screen time entry
     * @param duration The duration of the screen time entry
     */
    public void setDuration(long duration){
        this.duration = duration;
    }

    /**
     * Set the start time of the screen time entry
     * @param startTime The start time of the screen time entry
     */
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    /**
     * Constructor for the ScreenTimeEntry
     * @param userId The user id
     * @param applicationName The application name
     * @param duration The duration
     * @param startTime The start time
     */
    public ScreenTimeEntry(int userId, String applicationName, long duration, LocalDateTime startTime) {
        this.userId = userId;
        this.applicationName = applicationName;
        this.duration = duration;
        this.startTime = startTime;
    }




}
