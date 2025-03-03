package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;

/**
 * @author denzel
 * @author Mahin Patel
 */
public class ShiftDAO {

    private static final String QUERY_FIND_SHIFT = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_EMPLOYEE = "SELECT * FROM employee WHERE badgeid =?";

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
     * @param shiftId The ID of the shift to retrieve.
     * @return The Shift object associated with the given shift ID, or null if not found.
     */
    public Shift find(int shiftId) {
        Shift shift = null;
        Connection cont = null; // Declaring connection here

        try {
            cont = daoFactory.getConnection(); // Get connection
            PreparedStatement preparedStatement = cont.prepareStatement(QUERY_FIND_SHIFT);
            preparedStatement.setInt(1, shiftId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    shift = mapResultSetToShift(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return shift;
    }

    /**
     * Finds a Shift based on the Badge ID.
     * 
     * @param badge The Badge object containing the badge ID.
     * @return The Shift object associated with the given badge ID, or null if not found.
     */
    public Shift find(Badge badge) {
        Shift shift = null;
        Connection cont = null; // Declaring connection here

        try {
            cont = daoFactory.getConnection(); // Get connection
            PreparedStatement preparedStatement = cont.prepareStatement(QUERY_FIND_EMPLOYEE);
            preparedStatement.setString(1, badge.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int shiftId = resultSet.getInt("shiftid");
                    shift = find(shiftId); //find the shift with the shift ID
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } 

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

        shiftData.put("shiftid", resultSet.getInt("id"));
        shiftData.put("description", resultSet.getString("description"));
        shiftData.put("shiftstart", resultSet.getObject("shiftstart", LocalTime.class));
        shiftData.put("shiftstop", resultSet.getObject("shiftstop", LocalTime.class));
        shiftData.put("roundinterval", resultSet.getInt("roundinterval"));
        shiftData.put("graceperiod", resultSet.getInt("graceperiod"));
        shiftData.put("dockpenalty", resultSet.getInt("dockpenalty"));
        shiftData.put("lunchstart", resultSet.getObject("lunchstart", LocalTime.class));
        shiftData.put("lunchstop", resultSet.getObject("lunchstop", LocalTime.class));
        shiftData.put("lunchthreshold", resultSet.getInt("lunchthreshold"));

        return new Shift(shiftData);
        
    }
    
}
