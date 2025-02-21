/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Punch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.*;
import java.util.*;

/**
 *
 * @author evanranjitkar
 */
public class PunchDAO {
    
    private static final String Query_INSERT = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?,?,?,?);";
    private static final String Query_FIND = "SELECT * FROM event WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Punch find (int id){
        
        Punch resultPunch = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_INSERT);
                
                // Providing the arguments for the PreparedStatement
                ps.setInt(1, id);
                
                // Executing the query and executeUpdate() returns the number of
                // row affected by the query so storing it in an int variable
                boolean hasResults = ps.execute();
                
                if (hasResults){
                    
                    rs = ps.getResultSet();
                    
                    while(rs.next()){
                        
                        System.out.println(rs);
                    }
                    
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return resultPunch;
    }
    
    public int create(Punch punchObject){
        
        // Getting the terminalid from the punchObeject and 
        // storing it in an int variable
        int terminalId = punchObject.getTerminalId();
        
        int departmentId = 0;
        
        int resultId = 0;
        
        if(terminalId != departmentId || terminalId != 0){
            return resultId;
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_INSERT);
                
                // Providing the arguments for the PreparedStatement
                ps.setInt(1, punchObject.getTerminalId());
                ps.setString(2, punchObject.getBadge().toString());
                ps.setString(3, punchObject.getOriginalTimeStamp().toString());
                ps.setString(4, punchObject.getPunchType().toString());
                
                
                // Executing the query and executeUpdate() returns the number of
                // row affected by the query so storing it in an int variable
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected == 0){
                    return resultId;
                }
                
                rs = ps.getResultSet();
                
                while(rs.next()){
                    
                    resultId = rs.getInt(1);
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return resultId;
        
    }
}
