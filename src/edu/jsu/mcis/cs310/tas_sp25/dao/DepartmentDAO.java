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
    
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    private final DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

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
