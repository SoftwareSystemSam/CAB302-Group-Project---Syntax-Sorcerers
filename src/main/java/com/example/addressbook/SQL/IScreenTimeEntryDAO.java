package com.example.addressbook.SQL;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IScreenTimeEntryDAO{
    void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException;

    List<ScreenTimeEntry> getScreenTimeEntriesByUserId(int userId) throws SQLException;

    List<ScreenTimeEntry> getScreenTimeEntriesByUserIdAndDate(int userId, LocalDate date) throws SQLException;

    void upsertScreenTimeEntry(int userId, String applicationName, long duration, LocalDateTime dateTime) throws SQLException;

    Map<DayOfWeek, Long> getWeeklyScreenTimeByUserId(int userId, LocalDate startOfWeek) throws SQLException;




    LocalDateTime findMostRecentStartTimeByUserAppAndDate(int userId, String applicationName, LocalDate date) throws SQLException;
    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}