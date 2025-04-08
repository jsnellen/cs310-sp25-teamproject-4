package edu.jsu.mcis.cs310.tas_sp25.dao;
import edu.jsu.mcis.cs310.tas_sp25.*;
import java.sql.*;
import java.time.LocalDateTime;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import edu.jsu.mcis.cs310.tas_sp25.Department;
import edu.jsu.mcis.cs310.tas_sp25.Badge;
import edu.jsu.mcis.cs310.tas_sp25.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp25.Employee;

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
    
    /**
         * Finds an Employee from the database with the id
         * @param id The id for the employee
         * @return Employee
    */
    
    public Employee find(int id){
        
        // Store the retrieved Employee object
        Employee employee = null;
        
        // SQL query to find an Employee by ID 
        String query = "Select * FROM employee WHERE id = ?";
        
        Connection conn = daoFactory.getConnection();
        
        try(
                
        PreparedStatement pst = conn.prepareStatement(query)){
            
            // Providing the arguments for the PreparedStatement
            pst.setInt(1, id);
            
            // Executing the PreparedStatement and storing the result in the ResultSet
            ResultSet rs = pst.executeQuery();
            
            // Looping through the result set
            if (rs.next()){
                
                // Extracting employee data from database
                String firstname = rs.getString("firstname");
                
                String middlename = rs.getString("middlename");
                
                // if middlename is null, then initialize it to an empty string
                if (middlename == null){
                    middlename = "";
                }
                
                // Storing the data in their respective variables
                String lastname = rs.getString("lastname");
                
                LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();
                
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
                
                // Checking the employee type  
                EmployeeType employeeType = (typeId == 0) ? EmployeeType.PART_TIME : EmployeeType.FULL_TIME;
                             
                // Ensures that all dependent objects are valid before creating Employee, if they are null nothing happens
                if (badge != null && department != null){
                    employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
                }
                
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employee;
    }
    
    /**
         * Finds an Employee from the database with the badge
         * @param badge The badge object for the employee instance
         * @return Employee
    */
    public Employee find(Badge badge){
        
        if (badge == null) return null;
        
        // SQL query to find an Employee by badgeid
        String query = "SELECT id FROM employee WHERE badgeid = ?";
        
        // Declaring an employee object and initializing it to null
        Employee employee = null;
        
        Connection conn = daoFactory.getConnection();
        
        try(
        PreparedStatement pst = conn.prepareStatement(query)){
            
            // Providing the arguments for the PreparedStatement
            pst.setString(1, badge.getId());
            
            // Executing the PreparedStatement and storing the result in the ResultSet
            ResultSet rs = pst.executeQuery();
            
            // Looping through the result set
            if (rs.next()){
                
                // Getting the employeeid from the ResultSet
                int id = rs.getInt("id");
                
                // Using the find method which uses employeeid to find an employee
                employee = this.find(id);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employee;
    }
}
