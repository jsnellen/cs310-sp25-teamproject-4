package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.PunchDAO;
import edu.jsu.mcis.cs310.tas_sp25.dao.DAOUtility;
import edu.jsu.mcis.cs310.tas_sp25.dao.AbsenteeismDAO;
import edu.jsu.mcis.cs310.tas_sp25.dao.EmployeeDAO;
import edu.jsu.mcis.cs310.tas_sp25.dao.DAOFactory;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit test class for verifying absenteeism calculations in the TAS system.
 * 
 * <p>This class uses data access objects (DAOs) to retrieve punch, employee,
 * and absenteeism data, and calculates absenteeism percentages over a given 
 * pay period. It validates the results against expected string outputs.</p>
 * 
 * <p>The class includes tests for employees with various shift types and pay 
 * period scenarios (weekdays, weekends, etc.).</p>
 * 
 * Uses JUnit 4 framework.
 * @author Cole Stephens
 */
public class AbsenteeismTest {
    
    private DAOFactory daoFactory;

    /**
     * Sets up the DAOFactory before each test to prepare DAO access.
     */
    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }
    
    /**
     * Tests absenteeism calculation for an employee on Shift 1 during a weekday pay period.
     * 
     * <p>Checks the absenteeism percentage and verifies the correct value is inserted
     * and retrieved from the database.</p>
     * 
     * Expected absenteeism value: 2.50%
     */
    @Test
    public void testAbsenteeismShift1Weekday() {
        
        AbsenteeismDAO absenteeismDAO = daoFactory.getAbsenteeismDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();
		
        /* Get Punch/Employee Objects */
        
        Punch p = punchDAO.find(3634);
        Employee e = employeeDAO.find(p.getBadge());
        Shift s = e.getShift();
        Badge b = e.getBadge();
        
        /* Get Pay Period Punch List */
        
        LocalDate ts = p.getOriginaltimestamp().toLocalDate();
        LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        
        ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);
        
        /* Adjust Punch List */
        
        for (Punch punch : punchlist) {
            punch.adjust(s);
        }
        
        /* Compute Pay Period Total Absenteeism */
        
        BigDecimal percentage = DAOUtility.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(e, ts, percentage);
        absenteeismDAO.create(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = absenteeismDAO.find(e, ts);
        
        /* Compare to Expected Value */
        
        assertEquals("#28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%", a2.toString());
        
    }
    /**
     * Test absenteeism calculation for an employee on Shift 1 during a weekend pay period.
     * 
     * <p>Checks if the absenteeism value is correctly computed for an unusual schedule.
     * This may test cases where extra hours are worked (negative absenteeism).</p>
     * 
     * Expected absenteeism value: -20.00%
     */
    @Test
    public void testAbsenteeismShift1Weekend() {
        
        AbsenteeismDAO absenteeismDAO = daoFactory.getAbsenteeismDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Get Punch/Employee Objects */
        
        Punch p = punchDAO.find(1087);
        Employee e = employeeDAO.find(p.getBadge());
        Shift s = e.getShift();
        Badge b = e.getBadge();
        
        /* Get Pay Period Punch List */
        
        LocalDate ts = p.getOriginaltimestamp().toLocalDate();
        LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        
        ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);
        
        /* Adjust Punch List */
        
        for (Punch punch : punchlist) {
            punch.adjust(s);
        }
        
        /* Compute Pay Period Total Absenteeism */
        
        BigDecimal percentage = DAOUtility.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(e, ts, percentage);
        absenteeismDAO.create(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = absenteeismDAO.find(e, ts);
        
        /* Compare to Expected Value */
        
        assertEquals("#F1EE0555 (Pay Period Starting 08-05-2018): -20.00%", a2.toString());
        
    }
    
    /**
     * Tests absenteeism calculation for an employee on Shift 2 during a weekend pay period.
     * 
     * <p>Validates absenteeism tracking for non-standard shift hours on weekends.</p>
     * 
     * Expected absenteeism value: -28.75%
     */
    @Test
    public void testAbsenteeismShift2Weekend() {
        
        //System.err.println("testAbsenteeismShift2Weekend()");
        
        AbsenteeismDAO absenteeismDAO = daoFactory.getAbsenteeismDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Get Punch/Employee Objects */
        
        Punch p = punchDAO.find(4943);
        Employee e = employeeDAO.find(p.getBadge());
        Shift s = e.getShift();
        Badge b = e.getBadge();
        
        /* Get Pay Period Punch List */
        
        LocalDate ts = p.getOriginaltimestamp().toLocalDate();
        LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        
        ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);
        
        /* Adjust Punch List */
        
        for (Punch punch : punchlist) {
            punch.adjust(s);
        }
        
        /* Compute Pay Period Total Absenteeism */
        
        BigDecimal percentage = DAOUtility.calculateAbsenteeism(punchlist, s);
        
        /* Insert Absenteeism Into Database */
        
        Absenteeism a1 = new Absenteeism(e, ts, percentage);
        absenteeismDAO.create(a1);
        
        /* Retrieve Absenteeism From Database */
        
        Absenteeism a2 = absenteeismDAO.find(e, ts);

        /* Compare to Expected Value */
        
        assertEquals("#08D01475 (Pay Period Starting 09-16-2018): -28.75%", a2.toString());
        
    }
    
}