package com.example.addressbook.SQLTest;
import com.example.addressbook.SQL.SqliteUserDAO;
import com.example.addressbook.SQL.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class SqliteUserDAOEntryTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @Mock
    private Statement mockStatement;
    private SqliteUserDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);  // Simulate at least one row being returned initially.

        // Specifically match the column labels to return the correct data
        when(mockResultSet.getString(eq("email"))).thenReturn("user@example.com");
        when(mockResultSet.getString(eq("password"))).thenReturn("password");
        when(mockResultSet.getInt(eq("id"))).thenReturn(1);

        dao = new SqliteUserDAO(mockConnection);
    }



    @Test
    void testCreateTable() throws SQLException {

        verify(mockStatement, times(1)).execute(anyString());
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User(1, "user@example.com", "password");
        dao.addUser(user);
        verify(mockPreparedStatement).setString(1, user.getEmail());
        verify(mockPreparedStatement).setString(2, user.getPassword());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(1, "user@example.com", "password");
        dao.deleteUser(user);
        verify(mockPreparedStatement).setInt(1, user.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetUser() throws Exception {
        User result = dao.getUser(1);  // Assuming '1' is a valid user ID
        assertNotNull(result, "User should be found");
        assertEquals(1, result.getId(), "ID should match");
       assertEquals("user@example.com", result.getEmail(), "Email should match");
        assertEquals("password", result.getPassword(), "Password should match");

    }


    @Test
    void testGetUserByEmail() throws Exception {
        // Set up ResultSet to return data
        when(mockResultSet.next()).thenReturn(true);  // Simulates found user
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("email")).thenReturn("user@example.com");
        when(mockResultSet.getString("password")).thenReturn("password");

        User user = dao.getUserByEmail("user@example.com");
        assertNotNull(user);
        assertEquals("user@example.com", user.getEmail());
        assertEquals(1, user.getId());
        assertEquals("password", user.getPassword());


    }

    @Test
    void testGetUserNotFound() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);  // No user should be found
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        User result = dao.getUser(99);  // Assuming '1' is an ID that does not exist
        assertNull(result, "No user should be found with this ID");
    }

    @Test
    void testGetUserEmail() throws Exception {
        when(mockResultSet.next()).thenReturn(true); // Ensure the result set behaves as if a row is available
        when(mockResultSet.getString(eq("email"))).thenReturn("user@example.com");

        User result = dao.getUser(1); // Assuming '1' is a valid user ID

        System.out.println("Email from DAO: " + result.getEmail()); // Detailed logging
        assertEquals("user@example.com", result.getEmail(), "Email should match");
    }

    @Test
    void testGetUserPassword() throws Exception {
        when(mockResultSet.next()).thenReturn(true); // Ensure the result set behaves as if a row is available
        when(mockResultSet.getString(eq("password"))).thenReturn("password");

        User result = dao.getUser(1); // Assuming '1' is a valid user ID

        System.out.println("Password from DAO: " + result.getPassword()); // Detailed logging
        assertEquals("password", result.getPassword(), "Password should match");
    }

    @Test
    void testUpdateUser() throws Exception {
        // Create a User object with initial details
        User user = new User(1, "user@example.com", "oldPassword");
        String newPassword = "newPassword";

        // Call the method to be tested
        dao.updateUser(user, newPassword);

        // Verify that the correct SQL statement was prepared and executed
        verify(mockPreparedStatement).setString(1, newPassword);
        verify(mockPreparedStatement).setInt(2, user.getId());
        verify(mockPreparedStatement).executeUpdate();
    }


}
