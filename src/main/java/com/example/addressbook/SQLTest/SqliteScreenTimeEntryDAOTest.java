package com.example.addressbook.SQLTest;

import com.example.addressbook.SQL.ScreenTimeEntry;
import com.example.addressbook.SQL.SqliteScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class SqliteScreenTimeEntryDAOTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private SqliteScreenTimeEntryDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        dao = new SqliteScreenTimeEntryDAO(mockConnection);
    }

    @Test
    void testAddScreenTimeEntry() throws Exception {
        int userId = 1; // Example user ID

        // Create ScreenTimeEntry directly using the constructor with parameters
        ScreenTimeEntry entry = new ScreenTimeEntry(
                userId, // Pass userId directly
                "Google Chrome",
                3600L,
                LocalDateTime.parse("2019-03-27T10:15:30")
        );

        // Prepare the PreparedStatement mock to avoid null pointer exceptions
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Assume 1 row updated for success

        // Execute the method
        dao.addScreenTimeEntry(entry);

        // Verify that executeUpdate was called on the PreparedStatement
        verify(mockPreparedStatement).executeUpdate();
    }
}

