package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit test class for verifying the functionality of the BadgeDAO#find(String) method.
 * 
 * <p>Ensures correct retrieval of badge information based on badge ID.</p>
 */
public class BadgeFindTest {

    private DAOFactory daoFactory;

    /**
     * Sets up the DAO factory before each unit test to prepare DAO access. 
     */
    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    /**
     * Tests BageDAO#find(String) for badge ID "12565C60".
     * 
     * Expected result: #12565C60 (Chapman, Joshua E)
     */
    @Test
    public void testFindBadge1() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */

        Badge b1 = badgeDAO.find("12565C60");

        /* Compare to Expected Values */
        
        assertEquals("#12565C60 (Chapman, Joshua E)", b1.toString());

    }

    /**
     * Tests BadgeDAO#find(String) for badge ID "08D01475".
     * 
     * Expected result: #08D01475 (Littell, Amie D)
     */
    @Test
    public void testFindBadge2() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */

        Badge b2 = badgeDAO.find("08D01475");

        /* Compare to Expected Values */
        
        assertEquals("#08D01475 (Littell, Amie D)", b2.toString());

    }
    
    /**
     * Tests BadgeDAO#find(String) for badge ID "D2CC71D4".
     * 
     * Expected result: #D2CC71D4 (Lawson, Matthew J)
     */
    @Test
    public void testFindBadge3() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */

        Badge b3 = badgeDAO.find("D2CC71D4");

        /* Compare to Expected Values */
        
        assertEquals("#D2CC71D4 (Lawson, Matthew J)", b3.toString());

    }
    
}
