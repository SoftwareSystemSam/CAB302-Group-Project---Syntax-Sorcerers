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
        when(mockResultSet.next()).thenReturn(true, false);  // Return true first time (indicating a row exists), then false.
        when(mockResultSet.getString(anyString())).thenReturn("user@example.com");
        when(mockResultSet.getInt(anyString())).thenReturn(1);

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
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("email")).thenReturn("user@example.com");
        when(mockResultSet.getString("password")).thenReturn("password");

        User result = dao.getUser(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("user@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
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


    }

    @Test
    void testGetUserNotFound() throws Exception {

        when(mockResultSet.next()).thenReturn(false);  // No user found
        assertNull(dao.getUserByEmail("no_such_user@example.com"));
    }
}
