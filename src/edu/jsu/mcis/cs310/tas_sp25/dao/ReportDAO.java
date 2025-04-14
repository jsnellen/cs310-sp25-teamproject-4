package edu.jsu.mcis.cs310.tas_sp25.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp25.EmployeeType;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

/**
 * DAO for generating various JSON-based employee reports, including badge summaries, hours summaries, and real-time attendance status.
 * 
 *<p>Provides methods to retrieve data such as badge summaries, hours worked, and 
 * real-time attendance status ("Who's In/Out"). Results are returned as JSON strings</p>
 * 
 * @author evanranjitkar
 */
public class ReportDAO{
    
    private final DAOFactory daoFactory;
    
    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    private static final String Query_GETID = "SELECT badge.id as badgeId, badge.description AS employeeName, "
            + "department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "WHERE departmentid = ? "
            + "ORDER BY badge.description";
    
    private static final String Query_GETNULL = "SELECT badge.id as badgeId, badge.description AS employeeName,"
            + " department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "ORDER BY badge.description";
    
    private static final String Query_GET_HOURS = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift";
    
    private static final String QUERY_WHOS_IN_WHOS_OUT = "SELECT employee.badgeid AS badgeId, badge.description AS employeeName, "
            + "employee.firstname AS firstname, employee.lastname AS lastname, employeetype.description AS employeeType,"
            + "shift.description AS shift, event.timestamp AS arrived, eventtype.description AS eventType "
            + "FROM employee "
            + "JOIN badge ON employee.badgeid = badge.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "JOIN shift ON employee.shiftid = shift.id "
            + "LEFT JOIN event ON badge.id = event.badgeid AND event.timestamp <= ? "
            + "LEFT JOIN eventtype ON event.eventtypeid = eventtype.id "
            + "WHERE (? IS NULL OR employee.departmentid = ?) "
            + "ORDER BY employee.lastname, employee.firstname, event.timestamp DESC";
    
    /**
     * Retrieves a summary of employee badge information for a specified department.
     * 
     * @param departmentId Uses the ID of the department to filter employees to either include 
     * departments or by null.
     * @return A JSON-formatted string representing the employee badge summary.
     * 
     * @author Evan Ranjitkar
     */
    public String getBadgeSummary(Integer departmentId){
        
        
        JsonArray resultArray = new JsonArray();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {

                if(departmentId != null){
                    
                     ps = conn.prepareStatement(Query_GETID);
                     ps.setInt(1, departmentId);
                     
                } else {
                    ps = conn.prepareStatement(Query_GETNULL);
                }
                
                // Executing the PreparedStatement
                boolean hasResults = ps.execute();

                // If query has results, then retrieving the data
                if (hasResults){
                    
                // Getting result set and storing it in the ResultSet variable
                rs = ps.getResultSet();
                    
                while(rs.next()){
                    
                    JsonObject result = new JsonObject();
                    
                    result.put("badgeid",rs.getString("badgeId"));
                    result.put("name",rs.getString("employeeName"));
                    result.put("department",rs.getString("departmentName"));
                    result.put("type",rs.getString("employeeType"));
                    
                    resultArray.add(result);
                }
                
              }
           }
        }
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return Jsoner.serialize(resultArray);
    }
    
    
    public String getHoursSummary(LocalDate date, Integer departmentId, EmployeeType employeeType){
        
        JsonArray resultArray = new JsonArray();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {

                if(departmentId != null){
                    
                     ps = conn.prepareStatement(Query_GETID);
                     ps.setInt(1, departmentId);
                     
                } else {
                    ps = conn.prepareStatement(Query_GETNULL);
                }
                
                if(departmentId != null){
                    
                     ps = conn.prepareStatement(Query_GETID);
                     ps.setInt(1, departmentId);
                     
                } else {
                    ps = conn.prepareStatement(Query_GETNULL);
                }
                
                // Executing the PreparedStatement
                boolean hasResults = ps.execute();

                // If query has results, then retrieving the data
                if (hasResults){
                    
                // Getting result set and storing it in the ResultSet variable
                rs = ps.getResultSet();
                    
                while(rs.next()){
                    
                    JsonObject result = new JsonObject();
                    
                    result.put("badgeid",rs.getString("badgeId"));
                    result.put("name",rs.getString("employeeName"));
                    result.put("department",rs.getString("departmentName"));
                    result.put("type",rs.getString("employeeType"));
                    
                    resultArray.add(result);
                }
                
              }
           }
        }
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return Jsoner.serialize(resultArray);
    }
    /**
     * Generates a JSON report listing which employees are clocked ("In")
     * and which are clocked ("Out") at a specific date and time. Optionally filters
     * by department if a department ID is provided. 
     * 
     * @param dateTime The date and time to check employee statuses
     * @param departmentId The department ID to filter by (or null for all departments)
     * @return A JSON string representing the report data
     * 
     * @author Cole Stephens
     */
    
    public String getWhosInWhosOut(LocalDateTime dateTime, Integer departmentId) {
        
    JsonArray resultArray = new JsonArray(); //Result array to hold the final JSON objects
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Connection conn = daoFactory.getConnection();
        
        //Checks if the connection is valid before proceeding
        if (conn.isValid(0)) {
            
            //Prepares the query before passing the report date/time and department filter
            ps = conn.prepareStatement(QUERY_WHOS_IN_WHOS_OUT);
            ps.setTimestamp(1, Timestamp.valueOf(dateTime));
            ps.setObject(2, departmentId);
            ps.setObject(3, departmentId);

            rs = ps.executeQuery();
            
            //Uses a map to keep track of each employee by the badge ID while avoiding duplicate records
            HashMap<String, JsonObject> employeeMap = new HashMap<>();

            //Processes each row from the result set
            while (rs.next()) {
                
                String badgeId = rs.getString("badgeId");
                
                //Only processes the first occurence off each employee based on the latest event timestamp
                if (!employeeMap.containsKey(badgeId)) {
                    JsonObject obj = new JsonObject();
                    obj.put("firstname", rs.getString("firstname"));
                    obj.put("lastname", rs.getString("lastname"));
                    obj.put("badgeid", badgeId);
                    obj.put("employeetype", rs.getString("employeetype"));
                    obj.put("shift", rs.getString("shift"));
                    
                    //Determines employee's status based on their event type
                    String eventType = rs.getString("eventType");
                    String status = determineInOut(eventType);
                    obj.put("status", status);
                    
                    //If employee is currently "In". formats and stores their original arrival time
                    Timestamp arrived = rs.getTimestamp("arrived");
                    if (arrived != null && "In".equals(status)) {
                        LocalDateTime localArrival = arrived.toLocalDateTime();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
                        String formattedArrival = localArrival.format(formatter);
                        
                        //Formats the arrival time and manually capitalizes the day abbreviation
                        String finalArrival = formattedArrival.substring(0, 3).toUpperCase() + formattedArrival.substring(3);
                        
                        obj.put("arrived", finalArrival);
                    }
                    
                    //Adds employee record to the map
                    employeeMap.put(badgeId, obj);
                }
            }
                
            //Separates employee's into "In" and "Out" lists
            ArrayList<JsonObject> inList = new ArrayList<>();
            ArrayList<JsonObject> outList = new ArrayList<>();
                
            for (JsonObject obj : employeeMap.values()) {
                if ("In".equals(obj.get("status"))) {
                    inList.add(obj);
                } else {
                    outList.add(obj);
                }
            }
                
            //Defines the sort order: by employeetype, lastname, then firstname
            Comparator<JsonObject> employeeComparator = Comparator
                .comparing((JsonObject o) -> (String) o.get("employeetype"))
                .thenComparing((JsonObject o) -> (String) o.get("lastname"))
                .thenComparing((JsonObject o) -> (String) o.get("firstname"));

            //Sorts both lists accordingly
            inList.sort(employeeComparator);
            outList.sort(employeeComparator);

            //Combines the sorted lists into the final result array
            resultArray.addAll(inList);
            resultArray.addAll(outList);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (Exception e) { e.printStackTrace(); }
    }

    //Returns the serialized JSON string
    return Jsoner.serialize(resultArray);
}

/**
 * Helper method to determine employee "In" or "Out" status based on event type. 
 * 
 * @param eventType The event type string from the database
 * @return "In" if event type is "Clock In", otherwise "Out"
 * 
 * @author Cole Stephens
 */
private String determineInOut(String eventType) {
    return "Clock In".equals(eventType) ? "In" : "Out";
}

    
}
