package edu.jsu.mcis.cs310.tas_sp25.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    private static final String Query_GETID = "SELECT badge.id as badgeId, badge.description AS employeeName, "
            + "department.description AS departmentName, employeetype.description AS employeeType"
            + "FROM badge JOIN employee ON badge.id = employee.badgeid"
            + "JOIN department ON employee.departmentid = department.id"
            + "JOIN employeetype ON employee.employeetypeid = employeetypeid.id"
            + "WHERE departmentid = ?"
            + "ORDER BY badge.description";
    
    private static final String Query_GETNULL = "SELECT badge.id as badgeId, badge.description AS employeeName,"
            + " department.description AS departmentName, employeetype.description AS employeeType"
            + "FROM badge JOIN employee ON badge.id = employee.badgeid"
            + "JOIN department ON employee.departmentid = department.id"
            + "JOIN employeetype ON employee.employeetypeid = employeetypeid.id"
            + "ORDER BY badge.description";
    
    public JsonArray getBadgeSummary(Integer departmentId){
        
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
                
                rs = ps.executeQuery();
                
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
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return resultArray;
    }
}
