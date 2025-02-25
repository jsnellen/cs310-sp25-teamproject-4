package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EmployeeFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindEmployee1() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e1 = employeeDAO.find(14);

        /* Compare to Expected Values */
        
        assertEquals("ID #14: Donaldson, Kathleen C (#229324A4), Type: Full-Time, Department: Press, Active: 02/02/2017", e1.toString());

    }
    
    @Test
    public void testFindEmployee2() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("ADD650A8");
        Employee e2 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #82: Taylor, Jennifer T (#ADD650A8), Type: Full-Time, Department: Office, Active: 02/13/2016", e2.toString());

    }
    
    @Test
    public void testFindEmployee3() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e3 = employeeDAO.find(127);

        /* Compare to Expected Values */
        
        assertEquals("ID #127: Elliott, Nancy L (#EC531DE6), Type: Temporary / Part-Time, Department: Shipping, Active: 09/22/2015", e3.toString());

    }
    
    @Test
    public void testFindEmployee4() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("C1E4758D");
        Employee e4 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #93: Leist, Rodney J (#C1E4758D), Type: Temporary / Part-Time, Department: Warehouse, Active: 10/09/2015", e4.toString());

    }
    
        @Test
    public void testFindEmployee5() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e5 = employeeDAO.find(11);

        /* Compare to Expected Values */
        
        assertEquals("ID #11: Sanchez, Katherine H (#1B2052DE), Type: Full-Time, Department: Press, Active: 09/22/2015", e5.toString());

    }
    
            @Test
    public void testFindEmployee6() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e6 = employeeDAO.find(50);

        /* Compare to Expected Values */
        
        assertEquals("ID #50: Kaiser, Fay H (#76118CDC), Type: Full-Time, Department: Hafting, Active: 11/02/2015", e6.toString());

    }
    
    @Test
    public void testFindEmployee7() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("EC531DE6");
        Employee e7 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #127: Elliott, Nancy L (#EC531DE6), Type: Temporary / Part-Time, Department: Shipping, Active: 09/22/2015", e7.toString());

    }
    
    @Test
    public void testFindEmployee8() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();

        /* Retrieve Employee from Database (by ID) */

        Employee e8 = employeeDAO.find(25);

        /* Compare to Expected Values */
        
        assertEquals("ID #25: Smith, Patrick R (#3282F212), Type: Full-Time, Department: Maintenance, Active: 10/21/2015", e8.toString());

    }
    
        @Test
    public void testFindEmployee9() {
        
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Employee from Database (by badge) */

        Badge b = badgeDAO.find("07901755");
        Employee e9 = employeeDAO.find(b);

        /* Compare to Expected Values */
        
        assertEquals("ID #2: Terrell, Kenneth R (#07901755), Type: Full-Time, Department: Tool and Die, Active: 05/29/2016", e9.toString());
            
    }
    
    
    
}
