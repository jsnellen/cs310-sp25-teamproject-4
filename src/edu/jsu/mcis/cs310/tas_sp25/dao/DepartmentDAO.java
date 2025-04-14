/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * DAO for retrieving department records from the database.
 * <p>This class provides a method to retrieve a {@code Department} from the database
 * using its unique ID. It maps data from the {@code department} table to the 
 * {@code Department} model object.</p>
 * 
 * @author mahinpatel
 */
public class DepartmentDAO {
    
    // SQL query to fetch a department based on its ID
    private static final String QUERY_FIND_DEPARTMENT = "SELECT * FROM department WHERE id = ?";
    
    // DAOFactory instance to get database connections
    private final DAOFactory daoFactory;

    /**
     * Constructor for DepartmentDAO.
     * 
     * @param daoFactory The factory that provides access to the database connection.
     */
    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
   
    /**
     * Retrieves a department from the database using its ID.
     * 
     * @param departmentId The unique ID of the department.
     * @return A Department object if found, otherwise null.
     * @throws DAOException If there is an error retrieving the department.
     */
    public Department find(int departmentId) {
        
        PreparedStatement stmt = null;
        
        try{
            
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)){
            
            //Creating the query as a PreparedStatement
            stmt = conn.prepareStatement(QUERY_FIND_DEPARTMENT);
            
            // Providing the arguments for the PreparedStatement
            stmt.setInt(1, departmentId);

            // if ResultSet is valid, then procees
            try (ResultSet result_Set = stmt.executeQuery()) {
                
                // looping through the ResultSet
                if (result_Set.next()) {
                    
                    // Returning a new Department object with the data from the ResultSet
                    return new Department(
                        departmentId,
                        result_Set.getString("description"),
                        result_Set.getInt("terminalid")
                    );
                }
              }
           }
            
        } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        return null;
    }
}
