package com.example.addressbook.SQLTest;

import com.example.addressbook.SQL.ActiveWindowTracker;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.ScreenTimeEntry;
import com.example.addressbook.SQL.User;
import com.sun.jna.platform.win32.User32;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sun.jna.platform.win32.WinDef.HWND;

// https://site.mockito.org/

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActiveWindowTrackerTest {

    @Mock
    private IScreenTimeEntryDAO mockDao;
    @Mock
    private User32 mockUser32;

    @Mock
    private Statement mockStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private Connection mockConnection;
    private User testUser;
    private ActiveWindowTracker tracker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testUser = new User(1, "password", "testUser@hotmail.com");
        try {
            // Mock connection to return a mock statement
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Mock the PreparedStatement to avoid NullPointerException on setInt
            // Properly mock the setInt to do nothing as it's a void method
            doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());

            when(mockPreparedStatement.executeQuery()).thenReturn(mock(ResultSet.class));
            lenient().when(mockPreparedStatement.executeUpdate()).thenReturn(1); // assuming it successfully updates rows

            // Mock the Statement as needed (e.g., for createTable calls)
            when(mockStatement.execute(anyString())).thenReturn(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tracker = new ActiveWindowTracker(testUser, mockConnection);
    }

    @Test
    void testInitialization() {
        assertNotNull(tracker, "Tracker should be properly initialized");
    }

    @Test
    void testTrackActiveWindowNoUser() {
        // This setup assumes the tracker should not allow tracking without a logged-in user
        ActiveWindowTracker trackerWithNoUser = new ActiveWindowTracker(null, mockConnection);
        assertThrows(IllegalStateException.class, trackerWithNoUser::trackActiveWindow,
                "Should throw IllegalStateException if no user is logged in");
    }

    @Test
    void testActiveWindowChanges() throws Exception {
        HWND hwnd1 = new HWND();  // First window handle
        HWND hwnd2 = new HWND();  // Second window handle

        // Simulate the window handle change
        when(mockUser32.GetForegroundWindow()).thenReturn(hwnd1, hwnd1, hwnd2);

        // Mock GetWindowText to simulate window text changes
        when(mockUser32.GetWindowText(eq(hwnd1), any(char[].class), eq(512))).thenAnswer(invocation -> {
            char[] buffer = invocation.getArgument(1);
            Arrays.fill(buffer, ' ');
            "FirstWindow".getChars(0, "FirstWindow".length(), buffer, 0);
            return true;
        });

        when(mockUser32.GetWindowText(eq(hwnd2), any(char[].class), eq(512))).thenAnswer(invocation -> {
            char[] buffer = invocation.getArgument(1);
            Arrays.fill(buffer, ' ');
            "SecondWindow".getChars(0, "SecondWindow".length(), buffer, 0);
            return true;
        });

        tracker.trackActiveWindow();  // This should ideally be triggered by changes in GetForegroundWindow

        // Verify interactions
        verify(mockDao, times(2)).addScreenTimeEntry(any(ScreenTimeEntry.class));
    }




}


