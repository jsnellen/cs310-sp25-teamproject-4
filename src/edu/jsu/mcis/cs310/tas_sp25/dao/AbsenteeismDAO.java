/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp25.Badge;
import edu.jsu.mcis.cs310.tas_sp25.Department;
import edu.jsu.mcis.cs310.tas_sp25.Employee;
import edu.jsu.mcis.cs310.tas_sp25.EventType;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import edu.jsu.mcis.cs310.tas_sp25.dao.ReportDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing absenteeism records in the database.
 * 
 * <p>Provides methods to find and create absenteeism entries on an employee's ID and pay period.</p>
 * 
 * @author Evan Ranjitkar
 */
public class AbsenteeismDAO {
    
    private final DAOFactory daoFactory;
    
    AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    private static final String Query_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String Query_INSERT = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?,?,?)" 
                                                + "ON DUPLICATE KEY UPDATE percentage = ?";
    private static final String QUERY_CLEAR = "DELETE FROM absenteeism WHERE employeeid = ?";

    
    /**
         * Retrieves the absenteeism record for a given employee and pay period start date. 
         * 
         * @param employee The employee whose absenteeism record is being retrieved. 
         * @param payPeriodStartDate The starting date of the pay period (will be adjusted to the previous or same Sunday.)
         * @return the Absenteeism record, or null if none is found
         * 
         * @author Evan Ranjitkar
    */
    public Absenteeism find(Employee employee, LocalDate payPeriodStartDate){
        
        Absenteeism resultAbsenteesim = null;
        
        LocalDate adjustedPayPeriodStartDate = payPeriodStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            int employeeID = employee.getId();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(Query_FIND);
                
                ps.setInt(1, employeeID);
                ps.setDate(2, java.sql.Date.valueOf(payPeriodStartDate));
                
                boolean hasResults = ps.execute();
                
                if (hasResults){
                    
                    // Getting result set and storing it in the ResultSet variable
                    rs = ps.getResultSet();
                    
                    while(rs.next()){
                        
                        BigDecimal absenteeismPercentage = rs.getBigDecimal("percentage");
                    
                        resultAbsenteesim = new Absenteeism(employee, adjustedPayPeriodStartDate, absenteeismPercentage);
                    } 
                }
            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return resultAbsenteesim;
        
    }
    
     /**
         * Inserts a new absenteeism record into the database, or updates the existing record if one already exists.
         * 
         * @param absenteeismObject The Absenteeism object to be inserted or updated in the database.
         * @author evanranjitkar
    */
    public void create(Absenteeism absenteeismObject){

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(Query_INSERT);
                
                int employeeID = absenteeismObject.getEmployee().getId();
                
                LocalDate date = absenteeismObject.getPayPeriodStartDate();
                
                BigDecimal percentage = absenteeismObject.getAbsenteeismPercentage();
                
                ps.setInt(1, employeeID);
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setBigDecimal(3, percentage);
                
                ps.setBigDecimal(4, percentage);
                
                int rowsAffected = ps.executeUpdate();

            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
    }
   
    public void clear(Integer employeeId) {
        
        PreparedStatement ps = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_CLEAR);
                ps.setInt(1, employeeId);
                
                int rowsAffected = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {try {ps.close(); } catch (Exception e) {e.printStackTrace(); } }
        }
        
    }
   
}



