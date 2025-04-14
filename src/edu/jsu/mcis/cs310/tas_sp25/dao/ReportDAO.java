package edu.jsu.mcis.cs310.tas_sp25.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp25.EmployeeType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author evanranjitkar
 */
public class ReportDAO{
    
    private final DAOFactory daoFactory;
    
    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    private static final String Query_GET_BADGE_BY_ID = "SELECT badge.id as badgeId, badge.description AS employeeName, "
            + "department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "WHERE departmentid = ? "
            + "ORDER BY badge.description";
    
    private static final String Query_GET_BADGE_BY_NULL = "SELECT badge.id as badgeId, badge.description AS employeeName,"
            + " department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "ORDER BY badge.description";
    
    private static final String Query_GET_HOURS_WITH_ID_AND_TYPE = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE departmentid = ? AND (timestamp >= ? AND timestamp <= ?) AND (employeetypeid = ?))"
            + " ORDER BY lastname, firstname, middlename";
    
    private static final String Query_GET_HOURS_WITH_ID = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " WHERE departmentid = ? AND (timestamp >= ? AND timestamp <= ?)"
            + " ORDER BY lastname, firstname, middlename";
    
    private static final String Query_GET_HOURS_WITH_TYPE = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE timestamp >= ? AND timestamp <= ?) AND (employeetypeid = ?))"
            + " ORDER BY lastname, firstname, middlename";
    
    private static final String Query_GET_HOURS_ALL = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE timestamp >= ? AND timestamp <= ?)"
            + " ORDER BY lastname, firstname, middlename";
    
    public String getBadgeSummary(Integer departmentId){
        
        
        JsonArray resultArray = new JsonArray();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {

                if(departmentId != null){
                    
                     ps = conn.prepareStatement(Query_GET_BADGE_BY_ID);
                     ps.setInt(1, departmentId);
                     
                } else {
                    ps = conn.prepareStatement(Query_GET_BADGE_BY_NULL);
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

                if(departmentId != null && employeeType != null){
                    
                     ps = conn.prepareStatement(Query_GET_HOURS_WITH_ID_AND_TYPE);
                     ps.setInt(1, departmentId);
                     
                } else if (departmentId != null && employeeType == null) {
                    
                    ps = conn.prepareStatement(Query_GET_HOURS_WITH_ID);
                    
                } else if (departmentId == null && employeeType != null){
                    
                    ps = conn.prepareStatement(Query_GET_HOURS_WITH_TYPE);
                    
                } else {
                    ps = conn.prepareStatement(Query_GET_HOURS_ALL);
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
}
