package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchCreateTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testCreatePunch1() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Punch p1 = new Punch(103, badgeDAO.find("021890C0"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimeStamp();
        int terminalid = p1.getTerminalId();
        EventType punchtype = p1.getEventType();

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginalTimeStamp();

        assertEquals(terminalid, p2.getTerminalId());
        assertEquals(punchtype, p2.getEventType());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }

    @Test
    public void testCreatePunch2() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Punch p1 = new Punch(103, badgeDAO.find("021890C0"), EventType.CLOCK_OUT);

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimeStamp();
        int terminalid = p1.getTerminalId();
        EventType punchtype = p1.getEventType();

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginalTimeStamp();

        assertEquals(terminalid, p2.getTerminalId());
        assertEquals(punchtype, p2.getEventType());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }

    /**
     *
     * @author evanranjitkar
     */
    @Test
    public void testCreatePunch3() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Punch p1 = new Punch(0, badgeDAO.find("021890C0"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimeStamp();
        int terminalid = p1.getTerminalId();
        EventType punchtype = p1.getEventType();

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginalTimeStamp();

        assertEquals(terminalid, p2.getTerminalId());
        assertEquals(punchtype, p2.getEventType());
        assertEquals(ots.format(dtf), rts.format(dtf));
    }

    @Test
    public void testCreatePunch4() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Punch p1 = new Punch(104, badgeDAO.find("021890C0"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimeStamp();
        int terminalid = p1.getTerminalId();
        EventType punchtype = p1.getEventType();

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        assertEquals(p2, null);
    }

    @Test
    public void testCreatePunch5() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        Punch p1 = new Punch(103, badgeDAO.find("0B8C3085"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimeStamp();
        int terminalid = p1.getTerminalId();
        EventType punchtype = p1.getEventType();

        /* Insert Punch Into Database */
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginalTimeStamp();

        assertEquals(terminalid, p2.getTerminalId());
        assertEquals(punchtype, p2.getEventType());
        assertEquals(ots.format(dtf), rts.format(dtf));
    }
}
