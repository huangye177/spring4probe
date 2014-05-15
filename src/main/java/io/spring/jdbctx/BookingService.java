package io.spring.jdbctx;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

public class BookingService
{
	@Autowired
    JdbcTemplate jdbcTemplate;

	/*
	 * @Transactional annotation: meaning that any failure causes the entire operation to roll back 
	 * to its previous state, and to re-throw the original exception. 
	 * 
	 * This means that none of the people will be added to BOOKINGS if one person fails to be added
	 */
    @Transactional
    public void book(String... persons) {
        for (String person : persons) {
            System.out.println("Booking " + person + " in a seat...");
            jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", person);
        }
    };

    public List<String> findAllBookings() {
        return jdbcTemplate.query("select FIRST_NAME from BOOKINGS", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("FIRST_NAME");
            }
        });
    }
}
