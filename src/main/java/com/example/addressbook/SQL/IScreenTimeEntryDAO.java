package com.example.addressbook.SQL;

import java.sql.SQLException;
import java.util.List;

public interface IScreenTimeEntryDAO{
    void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException;

    List<ScreenTimeEntry> getScreenTimeEntriesByUserId(int userId) throws SQLException;

    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}