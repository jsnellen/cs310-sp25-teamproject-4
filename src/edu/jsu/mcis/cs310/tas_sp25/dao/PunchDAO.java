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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author evanranjitkar
 */
public class PunchDAO {
    
    // Writing queries to pass into prepareStatement
    private static final String Query_INSERT = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?,?,?,?);";
    private static final String Query_FIND = "SELECT * FROM event WHERE id = ?";
    
    // Using DATE() to extract the date part of the timestamp
    private static final String Query_LIST = "SELECT * FROM event WHERE badgeid = ? and DATE(timestamp) = ? ORDER BY timestamp ASC"; 
    private static final String Query_LIST_FOLLOWING_DAY = "SELECT * FROM event WHERE badgeid = ? and DATE(timestamp) = ? ORDER BY timestamp ASC LIMIT 1"; 
    
    private static final String Query_LIST_BETWEEN = "SELECT * FROM event WHERE badgeid = ? and DATE(timestamp) BETWEEN ? AND ? ORDER BY timestamp ASC"; 
    
    private final DAOFactory daoFactory;
    
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
        
        int departmentTerminalId;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            // Getting the terminalid from the punchObeject and 
            // storing it in an int variable
            int terminalId = punchObject.getTerminalId(); 
            
            if (conn.isValid(0)) {
                
                // Getting the EmployeeDAO from daoFactory
                
                EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
                
                // Using the find method in EmployeeDAO to find the employee object with the same badge as the punch
                
                Employee employee = employeeDAO.find(punchObject.getBadge());

                // Creating a Department object and initializing it to the employees department
                Department employeeDepartment = employee.getDepartment();

                // Getting the Department's terminal id
                departmentTerminalId = employeeDepartment.getTerminalId();
                
                // Checking if the terminalId id of the punch matches the department terminal id
                // and if the departmentTerminalId is 0
                // If neither of these condition satisfies, then the punch is unauthorized
                if(departmentTerminalId != terminalId && terminalId != 0 ){
                    
                   return 0;
                   
                }
                
                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_INSERT, Statement.RETURN_GENERATED_KEYS);
                
                    // Providing the arguments for the PreparedStatement
                   
                    ps.setInt(1, punchObject.getTerminalId());
                    
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
    
    
    // Should return an ArrayList of Punch objects
    // Should take Badge object and LocalDate as arguments
    
    public ArrayList<Punch> list(Badge badge, LocalDate date){
        
        // Initializing an ArrayList to store the results
        ArrayList<Punch> punchList = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_LIST);
                
                // Providing the arguments for the PreparedStatement
                ps.setString(1, badge.getId());
                
                // Converting LocalDate to Date
                ps.setDate(2, java.sql.Date.valueOf(date));
                
                // Executing the PreparedStatement
                boolean hasResults = ps.execute();
                       
                // If query has results, then retrieving the data
                if (hasResults){
                    
                    
                    // Getting result set and storing it in the ResultSet variable
                    rs = ps.getResultSet();
                    
                    // while loop to loop through the result set
                    while(rs.next()){
                       
                        Punch resultSetPunch = null;
                        // Since we are creating existing punches, we can use the second constructor
                        //Punch(id, terminalId, badge, originaltimestamp, eventType)
                        
                        // Getting the terminalid and storing it in a variable
                        int id = rs.getInt("id");  
                        
                        // Getting the terminalid and storing it in a variable
                        int terminalId = rs.getInt("terminalid");      
                        
                        // Getting the eventtypeid and storing it in a variable
                        int eventTypeId = rs.getInt("eventtypeid");                      
                        
                        // Getting the originalTimeStamp and converting it to local time
                        // with the help of toLocalDateTime function
                        LocalDateTime originalTimeStamp = rs.getTimestamp("timestamp").toLocalDateTime(); 
                        
                        //Getting the eventType and mapping it to the EventType enum
                        EventType eventType = EventType.values()[eventTypeId];             
                        
                        // Creating a new Punch object and providing the data obtained from the resultSet to the constructor
                        resultSetPunch = new Punch(id, terminalId, badge, originalTimeStamp, eventType); 
                        
                        // Adding the new Punch object to the ArrayList
                        punchList.add(resultSetPunch);
                        
                    }
                    
                }
               
                // Getting the first punch from the following day
                LocalDate followingDay = date.plusDays(1);
                
                 //Creating the query as a PreparedStatement
                ps = conn.prepareStatement(Query_LIST_FOLLOWING_DAY);
                
                // Providing the arguments for the PreparedStatement
                ps.setString(1, badge.getId());
                
                // Converting LocalDate to Date
                ps.setDate(2, java.sql.Date.valueOf(followingDay));
                
                // Executing the PreparedStatement
                boolean followingDayHasResults = ps.execute();
                       
                // If query has results, then retrieving the data
                if (followingDayHasResults){
                    
                    // Getting result set and storing it in the ResultSet variable
                    ResultSet followingDayResultSet = ps.getResultSet();
               
                    while(followingDayResultSet.next()){
                        
                        // fd --> following day
                        // Getting the terminalid and storing it in a variable
                        int fdId = followingDayResultSet.getInt("id");  
                        
                        // Getting the terminalid and storing it in a variable
                        int fdTerminalId = followingDayResultSet.getInt("terminalid");      
                        
                        // Getting the eventtypeid and storing it in a variable
                        int fdEventTypeId = followingDayResultSet.getInt("eventtypeid");                     
                        
                        // Getting the originalTimeStamp and converting it to local time
                        // with the help of toLocalDateTime function
                        LocalDateTime fdOriginalTimeStamp = followingDayResultSet.getTimestamp("timestamp").toLocalDateTime(); 
                        
                        //Getting the eventType and mapping it to the EventType enum
                        EventType fdEventType = EventType.values()[fdEventTypeId];             
                        
                        // if eventType is CLOCK_OUT or TIME_OUT, creating a new punch and adding it to the end of the ArrayList
                        if(fdEventType == EventType.CLOCK_OUT || fdEventType == EventType.TIME_OUT){
                        
                            // Creating a new Punch object and providing the data obtained from the resultSet to the constructor
                            Punch followingDayPunch = new Punch(fdId, fdTerminalId, badge, fdOriginalTimeStamp,  fdEventType); 
                        
                            // Adding the new Punch object to the ArrayList
                            punchList.add(followingDayPunch);
                        
                        }
                    }
                }
            }
        } 
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        // Returning the ArrayList
        return punchList;
    }
    
    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end){
    
         // Initializing an ArrayList to store the results
        ArrayList<Punch> punchList_Int = new ArrayList<>();
       
        // for loop to loop through each date in the interval
        for (LocalDate date = begin; date.isBefore((end).plusDays(1)); date = date.plusDays(1)){

            // Using the list method to get an ArrayList of punches from the current date
            // And adding them to the punchList_Int ArrayList
            punchList_Int.addAll(list(badge, date));
        }
        
        // Returning the ArrayList
        return punchList_Int;
    }
}
