package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.*;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchList_IntervalTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

/**
 *
 * @author evanranjitkar
 *  @author mahin patel
 */
    @Test
    public void testFindPunchList1() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts_Begin = LocalDate.of(2018, Month.SEPTEMBER, 20);
        LocalDate ts_End = LocalDate.of(2018, Month.SEPTEMBER, 26);

        System.out.println(ts_Begin.toString() + "\n" +ts_End.toString());
        Badge b = badgeDAO.find("67637925");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts_Begin, ts_End);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(5177));
        p2.add(punchDAO.find(5267));
        p2.add(punchDAO.find(5324));
        p2.add(punchDAO.find(5392));
        p2.add(punchDAO.find(5461));
        p2.add(punchDAO.find(5526));
        p2.add(punchDAO.find(5598));
        p2.add(punchDAO.find(5649));
        p2.add(punchDAO.find(5748));
        p2.add(punchDAO.find(5821));
        p2.add(punchDAO.find(5923));
        p2.add(punchDAO.find(6010));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchList2() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate st = LocalDate.of(2018, Month.AUGUST, 4);
        LocalDate et = LocalDate.of(2018, Month.AUGUST, 7);

        Badge b = badgeDAO.find("28DC3FB8");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, st, et);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(497));
        p2.add(punchDAO.find(548));
        p2.add(punchDAO.find(550));
        p2.add(punchDAO.find(567));
        p2.add(punchDAO.find(602));
        p2.add(punchDAO.find(656));
        p2.add(punchDAO.find(661));
        p2.add(punchDAO.find(674));
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchList5() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate st = LocalDate.of(2018, Month.AUGUST, 5);
        LocalDate et = LocalDate.of(2018, Month.AUGUST, 7);

        Badge b = badgeDAO.find("0B8C3085");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, st, et);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(496));
        p2.add(punchDAO.find(586));
        p2.add(punchDAO.find(604));
        p2.add(punchDAO.find(689));
       
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    

}

