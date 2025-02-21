package edu.jsu.mcis.cs310.tas_sp25.dao;
import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;
import java.time.LocalDateTime;


/**
 *
 * @author Tanner Thomas
 */
public class EmployeeDAO {
    // DAOFactory for access to the database connection
    private final DAOFactory daoFactory;
    // Constructor
    public EmployeeDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Employee find(int id){
        // Store the retrieved Employee object
        Employee employee = null;
        // SQL query to find an Employee by ID 
        String query = "Select * FROM employee WHERE id = ?";
        
        
        try(Connection conn = daoFactory.getConnection();
        PreparedStatement pst = conn.prepareStatement(query)){
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                // Extract employee data from database
                String firstname = rs.getString("firstname");
                String middlename = rs.getString("middlename");
                String lastname = rs.getString("lastname");
                LocalDateTime active = rs.getTimeStamp("active").toLocalDateTime();
                String badgeId = rs.getString("badgeid");
                int departmentId = rs.getInt("departmentid");
                int shiftId = rs.getInt("shiftid");
                int typeId = rs.getInt("employeetypeid");
                
                // Fetches objects from respective DAO
                BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                ShiftDAO shiftDAO = daoFactory.getShiftDAO();
                Badge badge = badgeDAO.find(badgeId);
                Department department = departmentDAO.find(departmentId);
                Shift shift = shiftDAO.find(shiftId);
                
                // Checking the employee type, Prevents invalid values   
                EmployeeType employeeType;
                if (typeId == 1){
                    employeeType = EmployeeType.PART_TIME;
                } else {
                    employeeType = EmployeeType.FULL_TIME;
                }
                
                // Ensures that all dependent objects are valid before creating Employee, if they are null nothing happens
                if (badge != null && department != null && shift != null){
                    employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
                }
                
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employee;
    }
    
    public Employee find(Badge badge){
        if (badge == null) return null;
        
        String query = "SELECT id FROM employee WHERE badgeid = ?";
        Employee employee = null;
        
        try(Connection conn = daoFactory.getConnection();
        PreparedStatement pst = conn.prepareStatement(query)){
            
            pst.setString(1, badge.getId());
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                int id = rs.getInt("id");
                employee = find(id);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employee;
    }
}
