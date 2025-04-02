package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.*;
import java.time.DayOfWeek;

/**
 * The `ShiftDAO` class provides methods to interact with the database and
 * retrieve shift-related data. It uses SQL queries to fetch shift details and
 * maps them to `Shift` objects.
 *
 *
 * @author denzel
 * @author Mahin Patel
 */
public class ShiftDAO {

    // Define a SQL query to find a shift by its ID
    private static final String QUERY_FIND_SHIFT = "SELECT s.id, s.description, d.shiftstart, d.shiftstop, d.roundinterval, "
            + "d.graceperiod, d.dockpenalty, d.lunchstart, d.lunchstop, d.lunchthreshold "
            + "FROM shift s JOIN dailyschedule d ON s.dailyscheduleid = d.id WHERE s.id = ?";

    // Define a SQL query to find an employee by their badge ID
    private static final String QUERY_FIND_EMPLOYEE = "SELECT * FROM employee WHERE badgeid =?";

    private static final String QUERY_FIND_DAILYSCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";

    private final DAOFactory daoFactory;

    /**
     * Constructor to initialize DAOFactory.
     *
     * @param daoFactory The DAOFactory used to get database connections.
     */
    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Finds a Shift by its ID.
     *
     * @param shiftId The ID of the shift to retrieve.
     * @return The Shift object associated with the given shift ID, or null if
     * not found.
     */
    public Shift find(int shiftId) {
        Shift shift = null;
        Connection cont = null; //Declaring connection here

        try {
            cont = daoFactory.getConnection(); //Get connection
            PreparedStatement preparedStatement = cont.prepareStatement(QUERY_FIND_SHIFT); //Prepare the SQL query 
            preparedStatement.setInt(1, shiftId); //Set the shift ID parameter in the query

            //Execute the query and get the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    shift = mapResultSetToShift(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        //Return the Shift object (or null if not found)
        return shift;
    }

    /**
     * Finds a Shift based on the Badge ID.
     *
     * @param badge The Badge object containing the badge ID.
     * @return The Shift object associated with the given badge ID, or null if
     * not found.
     */
    public Shift find(Badge badge) {
        Shift shift = null; //Declare a Shift object
        Connection cont = null; //Declaring connection here

        try {
            cont = daoFactory.getConnection(); //Get connection
            PreparedStatement preparedStatement = cont.prepareStatement(QUERY_FIND_EMPLOYEE); //Prepare the SQL query
            preparedStatement.setString(1, badge.getId()); //Set the badge ID parameter

            //Execute the query and get the result set
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int shiftId = resultSet.getInt("shiftid");
                    shift = find(shiftId); //find the shift with the shift ID
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        // Return the Shift object (or null if not found)
        return shift;
    }

    /**
     *
     *
     * @param resultSet The ResultSet containing the shift data.
     * @return A Shift object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while reading from the ResultSet.
     */
    private Shift mapResultSetToShift(ResultSet resultSet) throws SQLException {
        HashMap<String, Object> shiftData = new HashMap<>();
        HashMap<String, String> scheduleMap = new HashMap<>();

        // Populate the HashMap with data from the ResultSet
        shiftData.put("shiftid", resultSet.getInt("id"));
        shiftData.put("description", resultSet.getString("description"));

        scheduleMap.put("shiftstart", resultSet.getString("shiftstart"));
        scheduleMap.put("shiftstop", resultSet.getString("shiftstop"));
        scheduleMap.put("roundinterval", String.valueOf(resultSet.getInt("roundinterval")));
        scheduleMap.put("graceperiod", String.valueOf(resultSet.getInt("graceperiod")));
        scheduleMap.put("dockpenalty", String.valueOf(resultSet.getInt("dockpenalty")));
        scheduleMap.put("lunchstart", resultSet.getString("lunchstart"));
        scheduleMap.put("lunchstop", resultSet.getString("lunchstop"));
        scheduleMap.put("lunchthreshold", String.valueOf(resultSet.getInt("lunchthreshold")));

        DailySchedule schedule = new DailySchedule(scheduleMap);

        // Create and return a new Shift object using the HashMap
        return new Shift(shiftData, schedule);

    }

    public Shift find(Badge badge, LocalDate date) {
        Shift baseShift = find(badge);

        Shift shift = new Shift(baseShift);

        Connection conn = null;

        try {
            conn = daoFactory.getConnection();

            String query = "SELECT * FROM scheduleoverride Where (badgeid IS NULL OR badgeid = ?) "
                    + "AND (start <= ? AND (end IS NULL OR end >= ?))";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, badge.getId());
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setDate(3, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int overrideDay = rs.getInt("day");
                    int dailyScheduleId = rs.getInt("dailyscheduleid");
                    
                    DailySchedule overrideSchedule = findDailyScheduleById(dailyScheduleId);

                    shift.setDailySchedule(DayOfWeek.of(overrideDay), overrideSchedule);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return shift;
    }

    private DailySchedule findDailyScheduleById(int id) {
        DailySchedule schedule = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_FIND_DAILYSCHEDULE);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                HashMap<String, String> scheduleMap = new HashMap<>();
                scheduleMap.put("shiftstart", rs.getString("shiftstart"));
                scheduleMap.put("shiftstop", rs.getString("shiftstop"));
                scheduleMap.put("roundinterval", String.valueOf(rs.getInt("roundinterval")));
                scheduleMap.put("graceperiod", String.valueOf(rs.getInt("graceperiod")));
                scheduleMap.put("dockpenalty", String.valueOf(rs.getInt("dockpenalty")));
                scheduleMap.put("lunchstart", rs.getString("lunchstart"));
                scheduleMap.put("lunchstop", rs.getString("lunchstop"));
                scheduleMap.put("lunchthreshold", String.valueOf(rs.getInt("lunchthreshold")));

                schedule = new DailySchedule(scheduleMap);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
        }
        return schedule;
    }

}
