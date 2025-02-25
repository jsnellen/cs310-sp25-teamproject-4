package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Shift;
import edu.jsu.mcis.cs310.tas_sp25.Badge;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 *The ShiftDAO class provides data access methods for interacting with the shift table in the database.
 * It allows retrieving shift information based on shift ID or employee badge ID.
 * 
 * @author denzel
 */
public class ShiftDAO {
    
    //SQL query to find a shift by its ID
    private static final String QUERY_FIND_ID = "SELECT * FROM shift WHERE id = ?";
    
    //SQL query to find a shift by an employee's badge ID
    private static final String QUERY_FIND_BADGE = "SELECT shift.* FROM shift INNER JOIN employee on shift.id = employee.shiftid WHERE employee.badgid = ?";
    
    //SQL query to retrieve the daily schedule for a shift
    private static final String QUERY_GET_DAILYSCHEDULE = "SELECT * FROM daileyschedule where id = ?";
    
    private static final String QUERY_GET_SCHEDULEOVERRIDE= "SELECT * FROM schedule WHERE ((? >= start) AND ((? <= end) OR (end IS NULL)))";
    
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
            
    public Shift find(int id){
        
        /**
         * Finds a shift in the database by its ID.
         * 
         * @param id The ID of the shift to retrieve.
         * @return A shift object representing the retrieved shift, or null if no shift is found.
         */
        Shift shift = null; //Intitalize the shift object to null
        
        PreparedStatement ps = null; //Prepared Statement for the first query
        ResultSet rs = null; //ResultSet for the first query
        
        PreparedStatement ps2 = null; //PreparedStatement for the second query
        ResultSet rs2 = null; //ResultSet for the second query
        
        try {
            
            //Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();
            
            //if the connection is valid
            if (conn.isValid(0)) {
            
                //Prepare the first query to find the shift by ID
                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id); //Set the shift ID parameter
                
                //Execute the query
                if (ps.execute()) {
                
                    //Gets the result
                    rs = ps.getResultSet();
                    
                    //Iterate through the result set
                    while (rs.next()) {
                    
                        //Prepare the second query to get the dailey schedule
                        ps2 = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                        ps2.setInt(1, rs.getInt("daileyscheduleid"));
                        
                        //Execute the 
                        if (ps2.execute()) {
                        
                            rs2 = ps2.getResultSet();
                            
                            while(rs2.next()) {
                            
                                shift = createShiftFromResultSet(rs, rs2);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e){
        
        } finally {
        
            if(rs != null) {
                try{
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try{
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        
        return shift;
    }
    
    //Find shift
    
    /**
     * A Find method which retrieves the shift instance from the database
     * @param badge The badge whose shift should be retrieved from the database
     * @return
    */
    
    public Shift find(Badge badge){
        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        
        try {
            
            //Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();
            
            //if the connection is valid
            if (conn.isValid(0)) {
            
                //Prepare the first query to find the shift by ID
                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId()); //Set the shift ID parameter
                
                //Execute the query
                if (ps.execute()) {
                
                    rs = ps.getResultSet();
                    
                    //Iterate through the result set
                    while (rs.next()) {
                    
                        ps2 = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                        ps2.setInt(1, rs.getInt("daileyscheduleid"));
                        
                        if (ps2.execute()) {
                        
                            rs2 = ps2.getResultSet();
                            
                            while(rs2.next()) {
                            
                                shift = createShiftFromResultSet(rs, rs2);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e){
        
        } finally {
        
            if(rs != null) {
                try{
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try{
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        
        return shift;
    }
    
    /**
     * 
     */
    
    public Shift find (Badge badge, LocalDate punchdate) {
    
        Shift shift = null;
        
        LocalDateTime punchdatetime = LocalDateTime.of(punchdate, LocalTime.MIN);
        
        java.sql.Timestamp punchdatets = java.sql.Timestamp.valueOf(punchdatetime);
        
            PreparedStatement ps = null;
        ResultSet rs = null;
        
        PreparedStatement ps2;
        ResultSet rs2;

        PreparedStatement ps3;
        ResultSet rs3;
         
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                
                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                if (ps.execute()) {

                    // get results and assign
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // create shift  
                        ps2 = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                        ps2.setInt(1, rs.getInt("daileyscheduleid"));
                        
                        if (ps2.execute()) {
                        
                            rs2 = ps2.getResultSet();
                            
                            while(rs2.next()){
                                
                                ps3 = conn.prepareStatement(QUERY_GET_SCHEDULEOVERRIDE);
                                ps3.setTimestamp(1, punchdatets);
                                ps3.setTimestamp(2, punchdatets);
                                
                                if (ps3.execute()) {
                                    
                                    rs3 = ps3.getResultSet();
                                    
                                    while(rs3.next()){
                                        
                                        shift = createShiftFromResultSet(rs, rs2, rs3, badge);
                                        
                                    }
                                    
                                    if(!(rs3.next())){
                                        
                                        shift = createShiftFromResultSet(rs, rs2);
                                        
                                    }
                                    
                                }
                                
                            }
                        
                        }
                        
                    }

                }
                    
            }

        } catch (SQLException e) {


        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return shift;

    }
    
    private Shift createShiftFromResultSet(ResultSet rs, ResultSet rs2) throws SQLException {
        
        Map<String, String> shiftInfo = new HashMap<>();

        shiftInfo.put("description", rs.getString("description"));
        shiftInfo.put("id", rs.getString("id"));
        shiftInfo.put("shiftstart", rs2.getString("shiftstart"));
        shiftInfo.put("shiftstop", rs2.getString("shiftstop"));
        shiftInfo.put("lunchstart", rs2.getString("lunchstart"));
        shiftInfo.put("lunchstop", rs2.getString("lunchstop"));
        shiftInfo.put("graceperiod", rs2.getString("graceperiod"));
        shiftInfo.put("roundinterval", rs2.getString("roundinterval"));
        shiftInfo.put("dockpenalty", rs2.getString("dockpenalty"));
        shiftInfo.put("lunchthreshold", rs2.getString("lunchthreshold"));
        
        return new Shift(shiftInfo);
        
    }
    
    private Shift createShiftFromResultSet(ResultSet rs, ResultSet rs2, ResultSet rs3, Badge badge) throws SQLException {
        
        Shift shift = null;
        
        // initializing ps and rs
        PreparedStatement ps = null;
        ResultSet rs4 = null;

        try {

            // initializing connection and query
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                // prepare statement
                ps = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                ps.setInt(1, rs3.getInt("dailyscheduleid"));

                if (ps.execute()) {
                    
                    // get result set and assign
                    rs4 = ps.getResultSet();
                    
                    while (rs4.next()) {

                        Map<String, String> shiftInfo = new HashMap<>();
                        Map<String, String> shiftExceptionInfo = new HashMap<>();

                        shiftInfo.put("description", rs.getString("description"));
                        shiftInfo.put("id", rs.getString("id"));
                        shiftInfo.put("shiftstart", rs2.getString("shiftstart"));
                        shiftInfo.put("shiftstop", rs2.getString("shiftstop"));
                        shiftInfo.put("lunchstart", rs2.getString("lunchstart"));
                        shiftInfo.put("lunchstop", rs2.getString("lunchstop"));
                        shiftInfo.put("graceperiod", rs2.getString("graceperiod"));
                        shiftInfo.put("roundinterval", rs2.getString("roundinterval"));
                        shiftInfo.put("dockpenalty", rs2.getString("dockpenalty"));
                        shiftInfo.put("lunchthreshold", rs2.getString("lunchthreshold"));

                        shift = new Shift(shiftInfo);
                        
                        if((badge.getId().equals(rs3.getString("badgeid"))) || (rs3.getString("badgeid") == null)){
                            
                            shiftExceptionInfo.put("shiftstart", rs4.getString("shiftstart"));
                            shiftExceptionInfo.put("shiftstop", rs4.getString("shiftstop"));
                            shiftExceptionInfo.put("lunchstart", rs4.getString("lunchstart"));
                            shiftExceptionInfo.put("lunchstop", rs4.getString("lunchstop"));
                            shiftExceptionInfo.put("graceperiod", rs4.getString("graceperiod"));
                            shiftExceptionInfo.put("roundinterval", rs4.getString("roundinterval"));
                            shiftExceptionInfo.put("dockpenalty", rs4.getString("dockpenalty"));
                            shiftExceptionInfo.put("lunchthreshold", rs4.getString("lunchthreshold"));

                            shift.setDailySchedule(rs3.getInt("day"), shiftExceptionInfo);

                        }
                        
                    }

                }

            }

        } catch (SQLException e) {

            //throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return shift;
        
    }
    
}
