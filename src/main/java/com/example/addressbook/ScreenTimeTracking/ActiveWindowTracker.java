package com.example.addressbook.ScreenTimeTracking;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.SqliteScreenTimeEntryDAO;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import java.time.LocalDateTime;

private IScreenTimeEntryDAO screenTimeEntryDAO = new SqliteScreenTimeEntryDAO();

public class ActiveWindowTracker {

    private User32 user32 = User32.INSTANCE;

    // https://stackoverflow.com/questions/5767104/using-jna-to-get-getforegroundwindow
    public void trackActiveWindow() {
        HWND hwnd = user32.GetForegroundWindow(); // Get the active window handle
        char[] windowText = new char[512];
        user32.GetWindowText(hwnd, windowText, 512);
        String wText = Native.toString(windowText).trim();

        // Start timer to track how long this window remains in the foreground
        long startTime = System.currentTimeMillis();
        HWND newHwnd;
        do {
            newHwnd = user32.GetForegroundWindow(); // Check if the active window has changed
            try {
                Thread.sleep(1000); // Check every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (hwnd.equals(newHwnd));

        long endTime = System.currentTimeMillis();
        long timeSpentInSeconds = (endTime - startTime) / 1000; // Convert time from milliseconds to seconds

        logWindowTime(wText, timeSpentInSeconds, LocalDateTime.now());
    }


    // Create new function to handle logging screen time tracking to appropriate user
    private void logWindowTime(String applicationName, long durationInSeconds, LocalDateTime startTime) {
        // Implement database logging here
        // This might involve creating a new `ScreenTimeEntry` object and saving it using a DAO or repository
         ScreenTimeEntry entry = new ScreenTimeEntry();

         entry.setUser(currentUser);
         entry.setApplicationName(applicationName);
         entry.setDuration(durationInSeconds);
         entry.setStartTime(startTime);

         // Uses DAO to add entry to database
         screenTimeEntryDAO.addScreenTimeEntry(entry);
    }
}
