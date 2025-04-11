package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;

/**
 * Data Access Object (DAO) for managing badge records in the database.
 * 
 * <p>Provides methods to find, create, update, and delete badge entries based on
 * an employee's badge ID and description.</p>
 * 
 * @author Evan Ranjitkar, Cole Stephens
 */
public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";
    private static final String QUERY_FIND_ID = "SELECT id FROM badge WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO badge (id, description) VALUES (?, ?) ";
    private static final String QUERY_UPDATE = "UPDATE badge SET description = ? WHERE id = ?";
    private static final String QUERY_NOT_EQUAL = "SELECT id FROM badge WHERE description = ? AND id <> ?";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    /**
         * Retrieves a badge record from the database based on the provided badge ID.
         * 
         * @param id The badge ID to search for.
         * @return a badge object if found, otherwise null. 
    */
    public Badge find(String id) {

        Badge badge = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        String description = rs.getString("description");
                        badge = new Badge(id, description);

                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

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

        return badge;

    }

    /**
         * Inserts a new badge record into the database. A new badge ID is generated based on the description.
         * Duplicate badge entries are not allowed.
         * 
         * @param badge a badge containing the employee's description.
         * @return true if the badge was successfully created; false if a duplicate exists.
         * 
         * @author Evan Ranjitkar
    */
    public boolean create(Badge badge){
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Creating a new badge with the description only constructor
        // The constructor will create a new badge id for the new badge
        Badge newBadge = new Badge(badge.getDescription());
        
        try {
            Connection conn = daoFactory.getConnection();
            
            //Checks for duplicate badges ~cStephens
             if (conn.isValid(0)) {
                 
                 ps = conn.prepareStatement(QUERY_FIND_ID);
                 ps.setString(1, badge.getId());
                 rs = ps.executeQuery();
                 
                if (rs.next()) {
                     return false;
                }

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(QUERY_CREATE);
                
                // Providing the arguments for the PreparedStatement
                ps.setString(1, newBadge.getId());
                ps.setString(2, newBadge.getDescription());
                
                // Executing the query and executeUpdate() returns the number of
                // row affected by the query so storing it in an int variable
                int rowsAffected = ps.executeUpdate();
                
                 // if the delete was successful, then return true
                if (rowsAffected == 1){
                    return true;
                } else return false;

            }
            
        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

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
        
        // Default return false
        return false;
    }
    
    /**
         * Updates the description of an existing badge record in the database.
         * If another badge already has the same description, the update is not performed.
         * 
         * @param badge The badge object containing updated information.
         * @return true if the update was successful; false if a conflict exists.
         * 
         * @author Cole Stephens
    */
    public boolean update(Badge badge){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {
                 
                 ps = conn.prepareStatement(QUERY_NOT_EQUAL);
                 ps.setString(1, badge.getDescription());
                 ps.setString(2, badge.getId());
                 rs = ps.executeQuery();
                 
                 if (rs.next()) {
                     return false;
                 }
                 ps.close();

                ps = conn.prepareStatement(QUERY_UPDATE);
                ps.setString(1, badge.getDescription());
                ps.setString(2, badge.getId());
                
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected == 1){
                    return true;
                } else return false;

            }
            
        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

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
        
        return false;
        
    }
    
     /**
      * Deletes a badge record from the database based on the provided badge ID.
      * 
      * @param id The badge ID of the record to delete.
      * @return true if the record was successfully deleted; false otherwise. 
      * 
      * @author Evan Ranjitkar
      */
     public boolean delete(String id){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(QUERY_DELETE);
                
                // Providing the arguments for the PreparedStatement
                ps.setString(1, id);
                
                // Executing the query and executeUpdate() returns the number of
                // row affected by the query so storing it in an int variable
                int rowsAffected = ps.executeUpdate();
                
                // if the delete was successful, then return true
                if (rowsAffected == 1){
                    return true;
                } else return false;

            }
            
        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

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
        
        // Default return false
        return false;
    }
}
