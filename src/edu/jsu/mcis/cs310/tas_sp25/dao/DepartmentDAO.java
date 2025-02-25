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
 * This class follows the DAO pattern to interact with the "department" table.
 * It retrieves department data and maps it to a Department model object.
 * @author mahinpatel
 */
public class DepartmentDAO {
    
    // SQL query to fetch a department based on its ID
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    // DAOFactory instance to get database connections
    private final DAOFactory daoFactory;

    /**
    * Constructor for DepartmentDAO
     * @param daoFactory The factory that provides access to the database connection
    */
    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
   
    /**
    * Retrieves a department from the database using its ID.
    * @param id The unique identifier of the department to find.
    * @return A Department object if found, otherwise null.
    */
    public Department find(int id) {
        Department department = null;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(QUERY_FIND)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String description = rs.getString("description");
                    int termid = rs.getInt("terminalid");
                    department = new Department(id, description, termid);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return department;
    }
}
