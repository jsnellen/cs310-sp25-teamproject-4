package edu.jsu.mcis.cs310.tas_sp25.dao;

import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.*;

/**
 * @author denzel
 * @author Mahin Patel
 */
public class ShiftDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_BADGE = "SELECT * FROM employee WHERE badgeid =?";
    
    private final DAOFactory daoFactory;
    
    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public Shift find(int id) {
      Shift shift = null;
      
      PreparedStatement ps = null;
      ResultSet rs = null;
      
      try {
          Connection conn = daoFactory.getConnection();
          
          if(conn.isValid(0)) {
              
              ps = conn.prepareStatement(QUERY_FIND);
              ps.setInt(1, id);
              
              boolean hasresults = ps.execute();
              
              if(hasresults) {
                  rs = ps.getResultSet();
                  
                  while (rs.next()) {
                      
                      HashMap<String, Object> hashmap = new HashMap<>();
                      hashmap.put("shiftid", rs.getInt("id"));
                      hashmap.put("description" , rs.getString("description"));
                      hashmap.put("shiftstart", LocalTime.parse(rs.getString("shiftstart")));
                      hashmap.put("shiftstop", LocalTime.parse(rs.getString("shiftstop")));
                      hashmap.put("roundinterval", rs.getInt("roundinterval"));
                      hashmap.put("graceperiod", rs.getInt("graceperiod"));
                      hashmap.put("dockpenalty", rs.getInt("dockpenalty"));
                      hashmap.put("lunchstart", LocalTime.parse(rs.getString("lunchstart")));
                      hashmap.put("lunchstop" , LocalTime.parse(rs.getString("lunchstop")));
                      hashmap.put("lunchthreshold", rs.getInt("lunchthreshold"));
                      shift = new Shift(hashmap);
                             
                      
                  }        
                  }
              }
          }catch (SQLException e) {
              throw new DAOException(e.getMessage());
      } finally{
          if(rs != null) {
              try{
                  rs.close();
              } catch (SQLException e) {
                  throw new DAOException(e.getMessage());
              }
          }
          if (ps != null) {
              try {
                  ps.close();
              } catch (SQLException e) {
                  throw new DAOException(e.getMessage());
              }
          }
      }
      return shift;
    }
    
    public Shift find(Badge badge) {
        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
             
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)) {
              
              ps = conn.prepareStatement(QUERY_BADGE);
              ps.setString(1, badge.getId());
              
              boolean hasresults = ps.execute();
              
              if(hasresults) {
                  
                rs = ps.getResultSet();
                rs.next();
                int shiftid = rs.getInt("shiftid");
                shift = this.find(shiftid);
        }
    }
} catch (SQLException e) {
              throw new DAOException(e.getMessage());
      } finally{
          if(rs != null) {
              try{
                  rs.close();
              } catch (SQLException e) {
                  throw new DAOException(e.getMessage());
              }
          }
          if (ps != null) {
              try {
                  ps.close();
              } catch (SQLException e) {
                  throw new DAOException(e.getMessage());
              }
          }
        }
        return shift;
    }
    
}
