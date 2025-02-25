/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.Badge;
import edu.jsu.mcis.cs310.tas_sp25.Department;
import edu.jsu.mcis.cs310.tas_sp25.Employee;
import edu.jsu.mcis.cs310.tas_sp25.EventType;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author evanranjitkar
 */
public class PunchDAO {
    
    // Writing queries to pass into prepareStatement
    private static final String Query_INSERT = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?,?,?,?);";
    private static final String Query_FIND = "SELECT * FROM event WHERE id = ?";
    
    public final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    // find method to find a punch event from the event table
    public Punch find (int id){
        
        // Declaring a varible of type Punch to store the result Punch
        Punch resultPunch = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_FIND);
                
                // Providing the arguments for the PreparedStatement
                ps.setInt(1, id);
                
               // Executing the PreparedStatement
                boolean hasResults = ps.execute();
                
                // If query has results, then retrieving the data
                if (hasResults){
                    
                    // Getting result set and storing it in the ResultSet variable
                    rs = ps.getResultSet();
                    
                    // while loop to loop through the result set
                    while(rs.next()){
                        
                        // Getting the terminalid and storing it in a variable
                        int terminalId = rs.getInt("terminalid");      
                        
                        // Getting the badgeid and storing it in a variable
                        String badgeId = rs.getString("badgeid");
                        
                        // Getting the eventtypeid and storing it in a variable
                        int eventTypeId = rs.getInt("eventtypeid");
                        
                        // Getting a BadgeDAO instance from the daoFactory
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();   
                        
                        // Using the BadgeDAO instance to create a new Badge object,
                        // then using its find method to find the badgeId
                        Badge badge = badgeDAO.find(badgeId);                         
                        
                        // Getting the originalTimeStamp and converting it to local time
                        // with the help of toLocalDateTime function
                        LocalDateTime originalTimeStamp = rs.getTimestamp("timestamp").toLocalDateTime(); 
                        
                        //Getting the eventType and mapping it to the EventType enum
                        EventType eventType = EventType.values()[eventTypeId];             
                        
                        // Creating a new Punch object and providing the data obtained from the resultSet as arguments
                        resultPunch = new Punch(id, terminalId, badge, originalTimeStamp, eventType);   
                      
                    }
                    
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        // Returning the Punch object
        return resultPunch;
    }
    
    public int create(Punch punchObject){
       
        int resultId = 0;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            // Getting the terminalid from the punchObeject and 
            // storing it in an int variable
            int terminalId = punchObject.getTerminalId(); 
            
            
            if (conn.isValid(0)) {

                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_INSERT, Statement.RETURN_GENERATED_KEYS);
                
                System.out.println("Before Employee");
             
                EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
                Employee employee = employeeDAO.find(Integer.parseInt(punchObject.getBadge().getId()));

               
            
                System.out.println("EMPLOYEE IS FULL");
                
                // Providing the arguments for the PreparedStatement
                ps.setInt(1, terminalId);
                ps.setString(2, punchObject.getBadge().getId());
                ps.setTimestamp(3, Timestamp.valueOf(punchObject.getOriginalTimeStamp()));
                ps.setInt(4, punchObject.getEventType().ordinal());
                
                
                // Executing the query and executeUpdate() returns the number of
                // row affected by the query so storing it in an int variable
                int rowsAffected = ps.executeUpdate();
             
                // If an error occurs during insertion, then returning 0
                if (rowsAffected == 0){
                    return 0;
                }
                
                // If insertion was successful, then getting the generated keys
                if (rowsAffected > 0) {
                    rs = ps.getGeneratedKeys();
                    
                    // Getting the id of the newly-inserted punch
                    if (rs.next()) {
                        resultId = rs.getInt(1);
                    }
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        // Returning the resultId or the numeric id of the newly-inserted punch
        return resultId;
        
    }
}
