package edu.jsu.mcis.cs310.tas_sp25.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
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
    
    private static final String Query_GETID = "SELECT * FROM EMPLOYEE WHERE departmentid = ?";
    private static final String Query_GETNULL = "SELECT * FROM EMPLOYEE";
    
    public JsonArray getBadgeSummary(Integer departmentId){
        
        JsonArray resultArray = new JsonArray();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
             if (conn.isValid(0)) {
                
                if(departmentId != null){
                     ps = conn.prepareStatement(Query_GETID);
                } else {
                    ps = conn.prepareStatement(Query_GETNULL);
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
