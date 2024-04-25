package com.example.addressbook.SQL;

import com.example.addressbook.ScreenTimeTracking.ScreenTimeEntry;

import java.sql.SQLException;

public interface IScreenTimeEntryDAO{
    void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException;

    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}