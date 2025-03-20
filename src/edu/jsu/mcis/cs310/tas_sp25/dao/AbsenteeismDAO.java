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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author evanranjitkar
 */
public class AbsenteeismDAO {
    
    private final DAOFactory daoFactory;
    
    AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    private static final String Query_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    private static final String Query_INSERT = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?,?,?)";
    
    public Absenteeism find(Employee employee, LocalDate payPeriodStartDate){
        
        Absenteeism resultAbsenteesim = null;
        
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
                    
                    BigDecimal absenteeismPercentage = rs.getBigDecimal("percentage");
                    
                    resultAbsenteesim = new Absenteeism(employee, payPeriodStartDate, absenteeismPercentage);
                    
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
                
                int rowsAffected = ps.executeUpdate();

            }
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
    }
}


