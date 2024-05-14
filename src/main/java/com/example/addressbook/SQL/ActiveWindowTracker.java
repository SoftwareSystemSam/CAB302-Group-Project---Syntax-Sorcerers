package com.example.addressbook.SQL;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class ActiveWindowTracker implements Runnable { // https://www.geeksforgeeks.org/runnable-interface-in-java/ need to add runnable for threading
    private IScreenTimeEntryDAO screenTimeEntryDAO;
    private User32 user32;
    private User currentUser;

    private volatile  boolean paused = false;

    private volatile boolean running = true; // Control flag to see when the program is running

    // Use this user when tracking active window
    public ActiveWindowTracker(User currentUser, Connection connection) {
        this.screenTimeEntryDAO = new SqliteScreenTimeEntryDAO(connection);
        this.user32 = User32.INSTANCE;
        this.currentUser = currentUser;

    }


    public void run() {
        while (running) {
            if (!paused) {
                try {
                    System.out.println("I am running the screen tracker.");
                    trackActiveWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }  else {
                try {
                    synchronized (this) {
                        while (paused) {
                            System.out.println("I have paused.");
                            wait();  // Wait until resume is called
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Handle thread interruption
                }
            }
        }
    }

    public synchronized void pause(){
        paused = true;
        System.out.println("Pause has been called.");
    }

    public synchronized void resume(){

        synchronized (this){
            paused = false;
            this.notifyAll();
            System.out.println("Resume has been called.");
        }
    }


    // https://stackoverflow.com/questions/5767104/using-jna-to-get-getforegroundwindow
    public void trackActiveWindow() throws SQLException {
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }
        HWND hwnd = user32.GetForegroundWindow();
        char[] windowText = new char[512];
        if (hwnd != null) {
            user32.GetWindowText(hwnd, windowText, 512);
        }
        String wText = hwnd != null ? Native.toString(windowText).trim() : "Unknown Window";
        long startTime = System.currentTimeMillis();

        while (!paused) {
            HWND newHwnd = user32.GetForegroundWindow();
            // Check if hwnd is null or its different from newhwnd
            if (hwnd == null || !hwnd.equals(newHwnd)) {
                long endTime = System.currentTimeMillis();
                long timeSpentInSeconds = (endTime - startTime) / 1000;
                if (hwnd != null) { // Only log if hwnd was initially non-null
                    logWindowTime(wText, timeSpentInSeconds, LocalDateTime.now());
                }
                hwnd = newHwnd; // Update hwnd to newhwnd whether its null or not
                if (hwnd != null) {
                    user32.GetWindowText(hwnd, windowText, 512);
                    wText = Native.toString(windowText).trim();
                } else {
                    wText = "Unknown Window"; // Handle case when hwnd is still null
                }
                startTime = System.currentTimeMillis();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


    // Create new function to handle logging screen time tracking to appropriate user
    private void logWindowTime(String applicationName, long durationInSeconds, LocalDateTime startTime) {
        try {
            if (currentUser == null) {
                System.err.println("No current user set for logging window time.");
                return;
            }

            int userId = currentUser.getId();  // Fetch the user ID from the currentUser
            LocalDate date = startTime.toLocalDate(); // Convert LocalDateTime to LocalDate

            // Uses DAO to upsert entry to database
            screenTimeEntryDAO.upsertScreenTimeEntry(userId, applicationName, durationInSeconds, startTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
