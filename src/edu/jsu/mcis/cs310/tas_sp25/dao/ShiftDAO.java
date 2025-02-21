package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Shift;
import edu.jsu.mcis.cs310.tas_sp25.Badge;
import java.sql.*;

/**
 *The ShiftDAO class provides data access methods for interacting with the shift table in the database.
 * It allows retrieving shift information based on shift ID or employee badge ID.
 * 
 * @author denze
 */
public class ShiftDAO {
    
    //SQL query to find a shift by its ID
    private static final String QUERY_FIND_ID = "SELECT * FROM shift WHERE id = ?";
    
    //SQL query to find a shift by an employee's badge ID
    private static final String QUERY_FIND_BADGE = "SELECT shift.* FROM shift INNER JOIN employee on shift.id = employee.shiftid WHERE employee.badgid = ?";
    
    //SQL query to retrieve the daily schedule for a shift
    private static final String QUERY_GET_DAILEYSCHEDULE = "SELECT * FROM daileyschedule where id = ?";
    
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
        
        PreparedStatment ps = null; //Prepared Statement for the first query
        ResultSet rs = null; //ResultSet for the first query
        
        PreparedStatement ps2 = null; //PreparedStatement for the second query
        ResultSet rs2 = null; //ResultSet for the second query
        
        try {
            
            //Get a connection from the DAOFactory
            Connection conn = daoFactory.getConnection();
            
            //if the connection is valid
            if (conn.isValid(0)) {
            
                //Prepare the first query to find the shift by ID
                ps = conn.preparedStatemnet(QUERY_FIND_ID);
                ps.setInt(1, id); //Set the shift ID parameter
                
                //Execute the query
                if (ps.execute()) {
                
                    rs = ps.getResultSet();
                    
                    //Iterate through the result set
                    while (rs.next()) {
                    
                        ps2 = conn.preparedStatement(QUERY_GET_DAILEYSCHEDULE);
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
}
