package com.example.addressbook.SQL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IScreenTimeEntryDAO{
    void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException;

    List<ScreenTimeEntry> getScreenTimeEntriesByUserId(int userId) throws SQLException;

    List<ScreenTimeEntry> getScreenTimeEntriesByUserIdAndDate(int userId, LocalDate date) throws SQLException;
    Map<String, ScreenTimeEntry> getMostUsedAppForEachDayOfWeek(int userId) throws SQLException;
    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}