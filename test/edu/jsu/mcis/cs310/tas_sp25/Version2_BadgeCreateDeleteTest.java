package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Version2_BadgeCreateDeleteTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testCreateBadge1() {

        /* Create Badges */

        Badge b1 = new Badge("Bies, Bill X");

        /* Compare Badge to Expected Value */
        
        assertEquals("#052B00DC (Bies, Bill X)", b1.toString());

    }
    
    @Test
    public void testCreateBadge2() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Badge Object */

        Badge b2 = new Badge("Smith, Daniel Q");
        
        /* Insert New Badge (delete first in case badge already exists) */
        
        badgeDAO.delete(b2.getId());
        boolean result = badgeDAO.create(b2);

        /* Compare Badge to Expected Value */
        
        assertEquals("#02AA8E86 (Smith, Daniel Q)", b2.toString());
        
        /* Check Insertion Result */
        
        assertEquals(true, result);

    }
    
    @Test
    public void testCreateBadge3() {

        /* Create Badges */

        Badge b3 = new Badge("Chris, Helms");

        /* Compare Badge to Expected Value */
        
        assertEquals("#C5C46574 (Chris, Helms)", b3.toString());

    }
    
    
    
    @Test
    public void testDeleteBadge1() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Badge Object */

        Badge b4 = new Badge("Haney, Debra F");
        
        /* Insert New Badge (delete first in case badge already exists) */
        
        badgeDAO.delete(b4.getId());
        badgeDAO.create(b4);
        
        /* Delete New Badge */
        
        boolean result = badgeDAO.delete(b4.getId());

        /* Compare Badge to Expected Value */
        
        assertEquals("#8EA649AD (Haney, Debra F)", b4.toString());
        
        /* Check Deletion Result */
        
        assertEquals(true, result);

    }
    
    @Test
    public void testDeleteBadge2() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Badge Object */

        Badge b5 = new Badge("Chris, Helms");
        
        /* Insert New Badge (delete first in case badge already exists) */
        
        badgeDAO.create(b5);
        
        /* Delete New Badge */
        
        boolean result = badgeDAO.delete(b5.getId());

        /* Compare Badge to Expected Value */
        
        assertEquals("#C5C46574 (Chris, Helms)", b5.toString());
        
        /* Check Deletion Result */
        
        assertEquals(true, result);

    }
    
    //~cStephens
    @Test
    public void testUpdate1() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        //Creates new object
        
        Badge b6 = new Badge("Black, Jack");
        
        //Inserts the new badge object
        
        badgeDAO.create(b6);
        
        //New updated object
        
        Badge updatedBadge = new Badge(b6.getId(), "Poppins, Mary");
        
        //Updates the existing description with a new description
        
        boolean result = badgeDAO.update(updatedBadge);
        
        //Compares and checks the results
        
        assertEquals("#2A16AC2F (Poppins, Mary)", updatedBadge.toString());
        assertEquals(true, result);
    }
    
    @Test
    public void updateTest2() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        Badge b7 = new Badge("Titus, Demetrian");
        Badge b8 = new Badge("Calgar, Marneus");
        
        badgeDAO.delete(b7.getId());
        badgeDAO.delete(b8.getId());
        
        badgeDAO.create(b7);
        badgeDAO.create(b8);
        
        Badge updatedBadge = new Badge(b8.getId(), "Titus, Demetrian");
        
        boolean result = badgeDAO.update(updatedBadge);
        System.out.println(updatedBadge);
        
        assertFalse(result);
        Badge fromDB = badgeDAO.find(b8.getId());
        System.out.println(fromDB);
        assertEquals("#4096CA05 (Calgar, Marneus)", fromDB.toString());
        
        
    }
}


    

