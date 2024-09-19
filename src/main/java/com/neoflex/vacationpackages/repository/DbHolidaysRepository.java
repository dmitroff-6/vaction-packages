package com.neoflex.vacationpackages.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DbHolidaysRepository implements HolidaysRepository{
    private final JdbcTemplate holidays;

    public DbHolidaysRepository(JdbcTemplate holidays) {
        this.holidays = holidays;
    }

    @Override
    public List<String> getHolidays() {
        String sql = "SELECT date FROM holidays;";
        return holidays.query(sql, this::mapRow);
    }

    private String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("date");
    }
}
