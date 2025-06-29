package edu.jsu.mcis.cs310.tas_sp25;

import edu.jsu.mcis.cs310.tas_sp25.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp25.dao.ReportDAO;
import java.time.LocalDateTime;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Unit test class for the ReportDAO "Who's In, Who's Out" report. 
 * 
 * <p> This class tests the retrieval and correctness of the employees status reports
 * at a given timestamp and department within the TAS system. It uses JUnit 4 for 
 * testing and JSON-simple for JSON parsing and comparison.</p>
 * <p>
 * The test compares actual report data retrieved from the database with predefined
 * expected JSON data to verify correctness.</p>
 * 
 */
public class Version2_WhosInWhosOutReportTest {

    private ReportDAO reportDAO;

    /**
     * Sets up the test environment by instantiating a DAOFactory and retrieves the
     * ReportDAO instance before each test is run.
     */
    @Before
    public void setup() {

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        reportDAO = daoFactory.getReportDAO();

    }
    
    /**
     * Unit test for the ReportDAO WhosInWhosOut method.
     * 
     * <p>This test verifies that the "Who's In, Who's Out" report generated for the 
     * assembly department at 7:00 AM on September 5th, 2018 and matches a predefined
     * expected JSON structure.</p>
     * 
     * <p>The expected JSON includes a list of employee records indicating their arrival
     * time, employment type, name, badge ID, shift, and current in/out status.</p>
     * 
     * <p>The test deserializes a hard coded JSON string representing the expected result,
     * retrieves the actual report from the DAO, deserializes it, and compares the two.</p>
     */
    @Test
    public void testWhosInWhosOutByDepartment1() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
        
            String jsonExpectedString = "[{\"arrived\":\"WED 09/05/2018 06:55:32\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Lee\",\"badgeid\":\"639D4185\",\"shift\":\"Shift 1\",\"lastname\":\"Gaines\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:46:41\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Andrea\",\"badgeid\":\"BE51FA92\",\"shift\":\"Shift 1\",\"lastname\":\"Harris\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:47:01\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"James\",\"badgeid\":\"DFDFE648\",\"shift\":\"Shift 1\",\"lastname\":\"Jinks\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:48:12\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Harry\",\"badgeid\":\"0B8C3085\",\"shift\":\"Shift 1\",\"lastname\":\"King\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:54:34\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ernest\",\"badgeid\":\"99F0C0FA\",\"shift\":\"Shift 1\",\"lastname\":\"Kite\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:52:24\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Robert\",\"badgeid\":\"4C459F1E\",\"shift\":\"Shift 2\",\"lastname\":\"Knaus\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:51:46\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Dorothy\",\"badgeid\":\"76E920D9\",\"shift\":\"Shift 2\",\"lastname\":\"Lambert\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:51:03\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Matthew\",\"badgeid\":\"D2CC71D4\",\"shift\":\"Shift 1\",\"lastname\":\"Lawson\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:55:12\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ali\",\"badgeid\":\"8D9E5710\",\"shift\":\"Shift 1\",\"lastname\":\"Melia\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:53:26\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mary\",\"badgeid\":\"B5A117E9\",\"shift\":\"Shift 1\",\"lastname\":\"Sullivan\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:54:59\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rose\",\"badgeid\":\"C278A564\",\"shift\":\"Shift 1\",\"lastname\":\"Hill\",\"status\":\"In\"},{\"arrived\":\"WED 09/05/2018 06:59:25\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Tracy\",\"badgeid\":\"1D52BFA2\",\"shift\":\"Shift 1\",\"lastname\":\"Lowery\",\"status\":\"In\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Lillian\",\"badgeid\":\"CEA28723\",\"shift\":\"Shift 1\",\"lastname\":\"Claude\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Judy\",\"badgeid\":\"8709982E\",\"shift\":\"Shift 1\",\"lastname\":\"Dent\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"James\",\"badgeid\":\"D928AFBA\",\"shift\":\"Shift 1\",\"lastname\":\"Ali\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Veronica\",\"badgeid\":\"8DB842EF\",\"shift\":\"Shift 1\",\"lastname\":\"Arney\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Kenneth\",\"badgeid\":\"C0063C4C\",\"shift\":\"Shift 1\",\"lastname\":\"Cain\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"George\",\"badgeid\":\"021890C0\",\"shift\":\"Shift 1\",\"lastname\":\"Chapell\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Tamara\",\"badgeid\":\"BD9BB983\",\"shift\":\"Shift 1\",\"lastname\":\"Dahl\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Freda\",\"badgeid\":\"C6C1C0F2\",\"shift\":\"Shift 1\",\"lastname\":\"Dickman\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Gloria\",\"badgeid\":\"6AA1785E\",\"shift\":\"Shift 1\",\"lastname\":\"Ellis\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Ronald\",\"badgeid\":\"6C0D1549\",\"shift\":\"Shift 1\",\"lastname\":\"Franklin\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Theresa\",\"badgeid\":\"0886BF12\",\"shift\":\"Shift 1\",\"lastname\":\"Gibson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rose\",\"badgeid\":\"29C3C7D4\",\"shift\":\"Shift 1\",\"lastname\":\"Gomez\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Wm\",\"badgeid\":\"3C03B8D7\",\"shift\":\"Shift 1\",\"lastname\":\"Howard\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Larry\",\"badgeid\":\"55BAF4B1\",\"shift\":\"Shift 1\",\"lastname\":\"Hoyt\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Thomas\",\"badgeid\":\"8A90B9A3\",\"shift\":\"Shift 1\",\"lastname\":\"Kawamoto\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Thomas\",\"badgeid\":\"C8E646D8\",\"shift\":\"Shift 1\",\"lastname\":\"Merrick\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Marcus\",\"badgeid\":\"45E5059F\",\"shift\":\"Shift 1\",\"lastname\":\"Osborne\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rosalee\",\"badgeid\":\"82A8539F\",\"shift\":\"Shift 1\",\"lastname\":\"Patterson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Earl\",\"badgeid\":\"618072EA\",\"shift\":\"Shift 1\",\"lastname\":\"Perales\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Elaine\",\"badgeid\":\"6CA0FF4A\",\"shift\":\"Shift 1\",\"lastname\":\"Pierce\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Sandra\",\"badgeid\":\"BAD139D6\",\"shift\":\"Shift 1\",\"lastname\":\"Sutherland\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Kelly\",\"badgeid\":\"E6386C7C\",\"shift\":\"Shift 1\",\"lastname\":\"Teel\",\"status\":\"Out\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Who's In, Who's Out" Report (2018-09-05 at 7:00am, Assembly Dept) */

            LocalDateTime ts = LocalDateTime.of(2018, 9, 5, 7, 0);

            String jsonActualString = reportDAO.getWhosInWhosOut(ts, 1);
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }

    /**
     * Unit test for the ReportDAO WhosInWhosOut method.
     * 
     * <p>This test verifies that the "Who's In, Who's Out" report generated for all 
     * the departments at 1030 AM on August 2nd, 2018 and matches a predefined
     * expected JSON structure.
     * </p>
     * 
     * <p>The expected JSON includes a list of employee records indicating their arrival
     * time, employment type, name, badge ID, shift, and current in/out status.
     * </p>
     * 
     * <p>The test deserializes a hard coded JSON string representing the expected result,
     * retrieves the actual report from the DAO, deserializes it, and compares the two.
     */
    @Test
    public void testWhosInWhosOutAll() {
        
        JsonArray jsonExpected = null, jsonActual = null;
        
        try {
        
            String jsonExpectedString = "[{\"arrived\":\"THU 08/02/2018 07:33:20\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Laurie\",\"badgeid\":\"4382D92D\",\"shift\":\"Shift 1 Early Lunch\",\"lastname\":\"Alvarez\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:34\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Charles\",\"badgeid\":\"95497F63\",\"shift\":\"Shift 1\",\"lastname\":\"Andrews\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:00:45\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Francis\",\"badgeid\":\"2986FF85\",\"shift\":\"Shift 1\",\"lastname\":\"Black\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:52:19\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Jose\",\"badgeid\":\"DFE4EB13\",\"shift\":\"Shift 1\",\"lastname\":\"Black\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:27\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Amy\",\"badgeid\":\"67637925\",\"shift\":\"Shift 1\",\"lastname\":\"Brenner\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:16\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Maria\",\"badgeid\":\"E8A58074\",\"shift\":\"Shift 1\",\"lastname\":\"Brown\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:00:27\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ernest\",\"badgeid\":\"D2C39273\",\"shift\":\"Shift 1\",\"lastname\":\"Buck\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:49:46\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Joy\",\"badgeid\":\"BEAFDB2F\",\"shift\":\"Shift 1\",\"lastname\":\"Clark\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:51:57\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Lillian\",\"badgeid\":\"CEA28723\",\"shift\":\"Shift 1\",\"lastname\":\"Claude\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:56:37\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"John\",\"badgeid\":\"0FFA272B\",\"shift\":\"Shift 1\",\"lastname\":\"Corwin\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:54\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mary\",\"badgeid\":\"E06BE060\",\"shift\":\"Shift 1\",\"lastname\":\"Dixon\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:51\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Kathleen\",\"badgeid\":\"229324A4\",\"shift\":\"Shift 1\",\"lastname\":\"Donaldson\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:41\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Chad\",\"badgeid\":\"935B799E\",\"shift\":\"Shift 1\",\"lastname\":\"Fisher\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:06\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Christine\",\"badgeid\":\"08D745A6\",\"shift\":\"Shift 1\",\"lastname\":\"Harris\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:46\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Dustin\",\"badgeid\":\"CEC9A3DA\",\"shift\":\"Shift 1\",\"lastname\":\"Hein\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:51:49\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"James\",\"badgeid\":\"DFDFE648\",\"shift\":\"Shift 1\",\"lastname\":\"Jinks\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:51\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Debra\",\"badgeid\":\"8C0644BA\",\"shift\":\"Shift 1\",\"lastname\":\"Jones\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:36\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Fay\",\"badgeid\":\"76118CDC\",\"shift\":\"Shift 1\",\"lastname\":\"Kaiser\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:53:23\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Harry\",\"badgeid\":\"0B8C3085\",\"shift\":\"Shift 1\",\"lastname\":\"King\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:52:42\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ernest\",\"badgeid\":\"99F0C0FA\",\"shift\":\"Shift 1\",\"lastname\":\"Kite\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:49:14\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Robert\",\"badgeid\":\"4C459F1E\",\"shift\":\"Shift 2\",\"lastname\":\"Knaus\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:01:15\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Dorothy\",\"badgeid\":\"76E920D9\",\"shift\":\"Shift 2\",\"lastname\":\"Lambert\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:46:54\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Matthew\",\"badgeid\":\"D2CC71D4\",\"shift\":\"Shift 1\",\"lastname\":\"Lawson\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:55:38\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ethel\",\"badgeid\":\"29C03912\",\"shift\":\"Shift 1\",\"lastname\":\"McKain\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:26\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Ali\",\"badgeid\":\"8D9E5710\",\"shift\":\"Shift 1\",\"lastname\":\"Melia\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:43\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Robert\",\"badgeid\":\"FF591F68\",\"shift\":\"Shift 1\",\"lastname\":\"Miller\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:55:20\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Dorothy\",\"badgeid\":\"F1EE0555\",\"shift\":\"Shift 1\",\"lastname\":\"Montgomery\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:54:00\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Sherri\",\"badgeid\":\"D1D2C387\",\"shift\":\"Shift 1\",\"lastname\":\"Omara\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:16\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Raymond\",\"badgeid\":\"2A7F5D99\",\"shift\":\"Shift 1\",\"lastname\":\"Palmer\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:44\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Anna\",\"badgeid\":\"922370AA\",\"shift\":\"Shift 1\",\"lastname\":\"Price\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:51:34\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Kathryn\",\"badgeid\":\"8C4CE4AC\",\"shift\":\"Shift 1\",\"lastname\":\"Rhoades\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:27\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Jeffery\",\"badgeid\":\"DD6E2C0C\",\"shift\":\"Shift 1\",\"lastname\":\"Rhodes\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:18\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Salvador\",\"badgeid\":\"CEBCC740\",\"shift\":\"Shift 1\",\"lastname\":\"Ryan\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:00:34\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Juan\",\"badgeid\":\"398B1563\",\"shift\":\"Shift 2\",\"lastname\":\"Sanchez\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:50:32\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mary\",\"badgeid\":\"4E04B5FE\",\"shift\":\"Shift 1\",\"lastname\":\"Smith\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:43\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Georgine\",\"badgeid\":\"AB8204A4\",\"shift\":\"Shift 1\",\"lastname\":\"Snively\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:42\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Bobbie\",\"badgeid\":\"CB99D1E8\",\"shift\":\"Shift 1\",\"lastname\":\"Speier\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:51:15\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Karen\",\"badgeid\":\"58EB7EA1\",\"shift\":\"Shift 1\",\"lastname\":\"Stalker\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:35:36\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Richard\",\"badgeid\":\"4DAC9951\",\"shift\":\"Shift 1\",\"lastname\":\"Stein\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:56:56\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mary\",\"badgeid\":\"B5A117E9\",\"shift\":\"Shift 1\",\"lastname\":\"Sullivan\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 08:23:07\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Kenneth\",\"badgeid\":\"07901755\",\"shift\":\"Shift 1\",\"lastname\":\"Terrell\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:52:45\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Janice\",\"badgeid\":\"FCE87D9F\",\"shift\":\"Shift 1\",\"lastname\":\"Tucker\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:00:03\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Tyson\",\"badgeid\":\"E880B82A\",\"shift\":\"Shift 1\",\"lastname\":\"Williams\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:49:26\",\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Matthew\",\"badgeid\":\"28DC3FB8\",\"shift\":\"Shift 1\",\"lastname\":\"Woods\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:57\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Amanda\",\"badgeid\":\"9E06A774\",\"shift\":\"Shift 1\",\"lastname\":\"Foster\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:00\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Doris\",\"badgeid\":\"3DA8B226\",\"shift\":\"Shift 1\",\"lastname\":\"Hamm\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:38\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Brad\",\"badgeid\":\"CF697DE6\",\"shift\":\"Shift 1\",\"lastname\":\"Hickmon\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:00:10\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Wm\",\"badgeid\":\"3C03B8D7\",\"shift\":\"Shift 1\",\"lastname\":\"Howard\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:59:54\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Larry\",\"badgeid\":\"55BAF4B1\",\"shift\":\"Shift 1\",\"lastname\":\"Hoyt\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:58:34\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Fredrick\",\"badgeid\":\"B09A75D7\",\"shift\":\"Shift 1\",\"lastname\":\"Lawrence\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:39\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Alison\",\"badgeid\":\"9973DBF1\",\"shift\":\"Shift 1\",\"lastname\":\"Miller\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:26:58\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Micheal\",\"badgeid\":\"E77BFAEA\",\"shift\":\"Shift 1\",\"lastname\":\"Weedman\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 07:02:45\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Margaret\",\"badgeid\":\"2A972897\",\"shift\":\"Shift 1\",\"lastname\":\"White\",\"status\":\"In\"},{\"arrived\":\"THU 08/02/2018 06:57:09\",\"employeetype\":\"Temporary Employee\",\"firstname\":\"Nadine\",\"badgeid\":\"B6902696\",\"shift\":\"Shift 1\",\"lastname\":\"Wright\",\"status\":\"In\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Alvina\",\"badgeid\":\"87176FD7\",\"shift\":\"Shift 1\",\"lastname\":\"Cordero\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Judy\",\"badgeid\":\"8709982E\",\"shift\":\"Shift 1\",\"lastname\":\"Dent\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Genoveva\",\"badgeid\":\"DF19620C\",\"shift\":\"Shift 1\",\"lastname\":\"Forte\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Nichole\",\"badgeid\":\"CBDE17A7\",\"shift\":\"Shift 1\",\"lastname\":\"Fox\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Lee\",\"badgeid\":\"639D4185\",\"shift\":\"Shift 1\",\"lastname\":\"Gaines\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Andrea\",\"badgeid\":\"BE51FA92\",\"shift\":\"Shift 1\",\"lastname\":\"Harris\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Jessie\",\"badgeid\":\"4E6E296E\",\"shift\":\"Shift 1\",\"lastname\":\"Hart\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Nicholas\",\"badgeid\":\"2CD387C2\",\"shift\":\"Shift 1\",\"lastname\":\"Horner\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Claire\",\"badgeid\":\"1C920A23\",\"shift\":\"Shift 1\",\"lastname\":\"Hutchison\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mitchell\",\"badgeid\":\"E216D413\",\"shift\":\"Shift 1\",\"lastname\":\"Jones\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Brian\",\"badgeid\":\"B4BBABEC\",\"shift\":\"Shift 1\",\"lastname\":\"Lawless\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"James\",\"badgeid\":\"9BFCB537\",\"shift\":\"Shift 1\",\"lastname\":\"Lilly\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Amie\",\"badgeid\":\"08D01475\",\"shift\":\"Shift 2\",\"lastname\":\"Littell\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Mildred\",\"badgeid\":\"CEDB6920\",\"shift\":\"Shift 1\",\"lastname\":\"Mills\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Katherine\",\"badgeid\":\"1B2052DE\",\"shift\":\"Shift 1\",\"lastname\":\"Sanchez\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Patrick\",\"badgeid\":\"3282F212\",\"shift\":\"Shift 1\",\"lastname\":\"Smith\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Jennifer\",\"badgeid\":\"ADD650A8\",\"shift\":\"Shift 1\",\"lastname\":\"Taylor\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Basil\",\"badgeid\":\"8E5F0240\",\"shift\":\"Shift 1\",\"lastname\":\"Thomas\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Travis\",\"badgeid\":\"C4F37EFF\",\"shift\":\"Shift 1\",\"lastname\":\"Welch\",\"status\":\"Out\"},{\"employeetype\":\"Full-Time Employee\",\"firstname\":\"Maria\",\"badgeid\":\"A5F194EB\",\"shift\":\"Shift 1\",\"lastname\":\"Ybarra\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Cruz\",\"badgeid\":\"9186E711\",\"shift\":\"Shift 1\",\"lastname\":\"Adams\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"James\",\"badgeid\":\"D928AFBA\",\"shift\":\"Shift 1\",\"lastname\":\"Ali\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Derrick\",\"badgeid\":\"2305D772\",\"shift\":\"Shift 1\",\"lastname\":\"Antonio\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Veronica\",\"badgeid\":\"8DB842EF\",\"shift\":\"Shift 1\",\"lastname\":\"Arney\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Laura\",\"badgeid\":\"88D12DCC\",\"shift\":\"Shift 1\",\"lastname\":\"Begaye\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Kenneth\",\"badgeid\":\"C0063C4C\",\"shift\":\"Shift 1\",\"lastname\":\"Cain\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"George\",\"badgeid\":\"021890C0\",\"shift\":\"Shift 1\",\"lastname\":\"Chapell\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Joshua\",\"badgeid\":\"12565C60\",\"shift\":\"Shift 1\",\"lastname\":\"Chapman\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Marian\",\"badgeid\":\"55B74EB5\",\"shift\":\"Shift 1\",\"lastname\":\"Coleman\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Peter\",\"badgeid\":\"AC239E44\",\"shift\":\"Shift 1\",\"lastname\":\"Cusson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Tamara\",\"badgeid\":\"BD9BB983\",\"shift\":\"Shift 1\",\"lastname\":\"Dahl\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Patricia\",\"badgeid\":\"C457EFF7\",\"shift\":\"Shift 1\",\"lastname\":\"Dam\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Lori\",\"badgeid\":\"4FA55999\",\"shift\":\"Shift 1\",\"lastname\":\"Davie\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Sarah\",\"badgeid\":\"CF1D8750\",\"shift\":\"Shift 1\",\"lastname\":\"Dearman\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Betty\",\"badgeid\":\"E70AD3D2\",\"shift\":\"Shift 1\",\"lastname\":\"Decker\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Freda\",\"badgeid\":\"C6C1C0F2\",\"shift\":\"Shift 1\",\"lastname\":\"Dickman\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Curtis\",\"badgeid\":\"2A5620A0\",\"shift\":\"Shift 1\",\"lastname\":\"Eaton\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Nancy\",\"badgeid\":\"EC531DE6\",\"shift\":\"Shift 1\",\"lastname\":\"Elliott\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Gloria\",\"badgeid\":\"6AA1785E\",\"shift\":\"Shift 1\",\"lastname\":\"Ellis\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Misty\",\"badgeid\":\"D4A2072B\",\"shift\":\"Shift 1\",\"lastname\":\"Ellis\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Nicholas\",\"badgeid\":\"124A2DED\",\"shift\":\"Shift 1\",\"lastname\":\"Ford\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Ronald\",\"badgeid\":\"6C0D1549\",\"shift\":\"Shift 1\",\"lastname\":\"Franklin\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Phillip\",\"badgeid\":\"DFD9BB5C\",\"shift\":\"Shift 1\",\"lastname\":\"Gallegos\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Christine\",\"badgeid\":\"73591F00\",\"shift\":\"Shift 1\",\"lastname\":\"Gauna\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Theresa\",\"badgeid\":\"0886BF12\",\"shift\":\"Shift 1\",\"lastname\":\"Gibson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rose\",\"badgeid\":\"29C3C7D4\",\"shift\":\"Shift 1\",\"lastname\":\"Gomez\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Edith\",\"badgeid\":\"9BD0258A\",\"shift\":\"Shift 1\",\"lastname\":\"Hearn\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rose\",\"badgeid\":\"C278A564\",\"shift\":\"Shift 1\",\"lastname\":\"Hill\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Thomas\",\"badgeid\":\"8A90B9A3\",\"shift\":\"Shift 1\",\"lastname\":\"Kawamoto\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Norman\",\"badgeid\":\"C47A78C1\",\"shift\":\"Shift 1\",\"lastname\":\"King\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rodney\",\"badgeid\":\"C1E4758D\",\"shift\":\"Shift 1\",\"lastname\":\"Leist\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Tracy\",\"badgeid\":\"1D52BFA2\",\"shift\":\"Shift 1\",\"lastname\":\"Lowery\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Patricia\",\"badgeid\":\"8D6362AD\",\"shift\":\"Shift 1\",\"lastname\":\"McGruder\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Thomas\",\"badgeid\":\"C8E646D8\",\"shift\":\"Shift 1\",\"lastname\":\"Merrick\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Margaret\",\"badgeid\":\"3437588A\",\"shift\":\"Shift 1\",\"lastname\":\"Mullen\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Paul\",\"badgeid\":\"31A25435\",\"shift\":\"Shift 1\",\"lastname\":\"Munday\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Marcus\",\"badgeid\":\"45E5059F\",\"shift\":\"Shift 1\",\"lastname\":\"Osborne\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Mauricio\",\"badgeid\":\"3E9E0E87\",\"shift\":\"Shift 1\",\"lastname\":\"Patterson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Rosalee\",\"badgeid\":\"82A8539F\",\"shift\":\"Shift 1\",\"lastname\":\"Patterson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Earl\",\"badgeid\":\"618072EA\",\"shift\":\"Shift 1\",\"lastname\":\"Perales\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Elaine\",\"badgeid\":\"6CA0FF4A\",\"shift\":\"Shift 1\",\"lastname\":\"Pierce\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Sarah\",\"badgeid\":\"9E0476B5\",\"shift\":\"Shift 1\",\"lastname\":\"Rivers\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Lawrence\",\"badgeid\":\"408B195F\",\"shift\":\"Shift 1\",\"lastname\":\"Robinson\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Jarvis\",\"badgeid\":\"9D527CFB\",\"shift\":\"Shift 1\",\"lastname\":\"Rodriquez\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Diana\",\"badgeid\":\"76B87761\",\"shift\":\"Shift 1\",\"lastname\":\"Rohrbach\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Raymond\",\"badgeid\":\"D4F37E6F\",\"shift\":\"Shift 1\",\"lastname\":\"Sanchez\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Willie\",\"badgeid\":\"860CBBEE\",\"shift\":\"Shift 1\",\"lastname\":\"Simmons\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Bonita\",\"badgeid\":\"690538BA\",\"shift\":\"Shift 1\",\"lastname\":\"Snow\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Michael\",\"badgeid\":\"8001201A\",\"shift\":\"Shift 1\",\"lastname\":\"Stevens\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Sandra\",\"badgeid\":\"BAD139D6\",\"shift\":\"Shift 1\",\"lastname\":\"Sutherland\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Elisa\",\"badgeid\":\"B7A6171D\",\"shift\":\"Shift 1\",\"lastname\":\"Taylor\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Steven\",\"badgeid\":\"93313988\",\"shift\":\"Shift 1\",\"lastname\":\"Taylor\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Kelly\",\"badgeid\":\"E6386C7C\",\"shift\":\"Shift 1\",\"lastname\":\"Teel\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Scotty\",\"badgeid\":\"7CB9642F\",\"shift\":\"Shift 1\",\"lastname\":\"Treat\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Carol\",\"badgeid\":\"0D87987C\",\"shift\":\"Shift 1\",\"lastname\":\"Trice\",\"status\":\"Out\"},{\"employeetype\":\"Temporary Employee\",\"firstname\":\"Ann\",\"badgeid\":\"E215F3DB\",\"shift\":\"Shift 1\",\"lastname\":\"Wright\",\"status\":\"Out\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            /* Get "Who's In, Who's Out" Report (2018-08-02 at 10:30am, All Dept) */

            LocalDateTime ts = LocalDateTime.of(2018, 8, 2, 10, 30);

            String jsonActualString = reportDAO.getWhosInWhosOut(ts, null);
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Compare to Expected Values */
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);

    }
    /**
     * Tests the "Who's In Who's Out" report for a department that has no employees.
     * 
     * <p>This test verifies that when a valid timestamp is provided but the department ID 
     * does not correspond to any employees, the resulting JSON array is empty.</p>
     * 
     * @author Cole Stephens
     */
    @Test
    public void testWhosInWhosOutEmptyDepartment() {
        
        JsonArray jsonActual = null; 
        
        try {
            
            //Get "Who's In Who's Out" Report (2018-11-1 at 12:00pm, no department)
            
            LocalDateTime ts = LocalDateTime.of(2018, 11, 1, 12, 0);
            
            String jsonActualString = reportDAO.getWhosInWhosOut(ts, 11);
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        //Compares expected values
        
        assertNotNull(jsonActual);
        assertTrue(jsonActual.isEmpty());
    }
    /**
     * Tests the "Who's In Who's Out" report for a timestamp before ant punch data exists.
     * 
     * <p>This test ensures that when requesting the report at a time before any time clock punches
     * have been recorded, the returned data reflects the default employee status "Out" for all employees.</p>
     * 
     * <p>The test deserializes a hard coded JSON string representing the expected result,
     * retrieves the actual report from the DAO, deserializes it, and compares the two.
     * 
     * @author Cole Stephens
     */
    @Test
    public void testWhosInWhosOutBeforeDataStart() {
        
        JsonArray jsonActual = null; JsonArray jsonExpected = null;
        
        try {
            
            String jsonExpectedString = "[{\"firstname\":\"Lillian\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"CEA28723\",\"shift\":\"Shift 1\",\"lastname\":\"Claude\",\"status\":\"Out\"},{\"firstname\":\"Judy\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"8709982E\",\"shift\":\"Shift 1\",\"lastname\":\"Dent\",\"status\":\"Out\"},{\"firstname\":\"Lee\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"639D4185\",\"shift\":\"Shift 1\",\"lastname\":\"Gaines\",\"status\":\"Out\"},{\"firstname\":\"Andrea\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"BE51FA92\",\"shift\":\"Shift 1\",\"lastname\":\"Harris\",\"status\":\"Out\"},{\"firstname\":\"James\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"DFDFE648\",\"shift\":\"Shift 1\",\"lastname\":\"Jinks\",\"status\":\"Out\"},{\"firstname\":\"Harry\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"0B8C3085\",\"shift\":\"Shift 1\",\"lastname\":\"King\",\"status\":\"Out\"},{\"firstname\":\"Ernest\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"99F0C0FA\",\"shift\":\"Shift 1\",\"lastname\":\"Kite\",\"status\":\"Out\"},{\"firstname\":\"Robert\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"4C459F1E\",\"shift\":\"Shift 2\",\"lastname\":\"Knaus\",\"status\":\"Out\"},{\"firstname\":\"Dorothy\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"76E920D9\",\"shift\":\"Shift 2\",\"lastname\":\"Lambert\",\"status\":\"Out\"},{\"firstname\":\"Matthew\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"D2CC71D4\",\"shift\":\"Shift 1\",\"lastname\":\"Lawson\",\"status\":\"Out\"},{\"firstname\":\"Ali\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"8D9E5710\",\"shift\":\"Shift 1\",\"lastname\":\"Melia\",\"status\":\"Out\"},{\"firstname\":\"Mary\",\"employeetype\":\"Full-Time Employee\",\"badgeid\":\"B5A117E9\",\"shift\":\"Shift 1\",\"lastname\":\"Sullivan\",\"status\":\"Out\"},{\"firstname\":\"James\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"D928AFBA\",\"shift\":\"Shift 1\",\"lastname\":\"Ali\",\"status\":\"Out\"},{\"firstname\":\"Veronica\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"8DB842EF\",\"shift\":\"Shift 1\",\"lastname\":\"Arney\",\"status\":\"Out\"},{\"firstname\":\"Kenneth\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"C0063C4C\",\"shift\":\"Shift 1\",\"lastname\":\"Cain\",\"status\":\"Out\"},{\"firstname\":\"George\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"021890C0\",\"shift\":\"Shift 1\",\"lastname\":\"Chapell\",\"status\":\"Out\"},{\"firstname\":\"Tamara\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"BD9BB983\",\"shift\":\"Shift 1\",\"lastname\":\"Dahl\",\"status\":\"Out\"},{\"firstname\":\"Freda\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"C6C1C0F2\",\"shift\":\"Shift 1\",\"lastname\":\"Dickman\",\"status\":\"Out\"},{\"firstname\":\"Gloria\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"6AA1785E\",\"shift\":\"Shift 1\",\"lastname\":\"Ellis\",\"status\":\"Out\"},{\"firstname\":\"Ronald\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"6C0D1549\",\"shift\":\"Shift 1\",\"lastname\":\"Franklin\",\"status\":\"Out\"},{\"firstname\":\"Theresa\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"0886BF12\",\"shift\":\"Shift 1\",\"lastname\":\"Gibson\",\"status\":\"Out\"},{\"firstname\":\"Rose\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"29C3C7D4\",\"shift\":\"Shift 1\",\"lastname\":\"Gomez\",\"status\":\"Out\"},{\"firstname\":\"Rose\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"C278A564\",\"shift\":\"Shift 1\",\"lastname\":\"Hill\",\"status\":\"Out\"},{\"firstname\":\"Wm\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"3C03B8D7\",\"shift\":\"Shift 1\",\"lastname\":\"Howard\",\"status\":\"Out\"},{\"firstname\":\"Larry\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"55BAF4B1\",\"shift\":\"Shift 1\",\"lastname\":\"Hoyt\",\"status\":\"Out\"},{\"firstname\":\"Thomas\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"8A90B9A3\",\"shift\":\"Shift 1\",\"lastname\":\"Kawamoto\",\"status\":\"Out\"},{\"firstname\":\"Tracy\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"1D52BFA2\",\"shift\":\"Shift 1\",\"lastname\":\"Lowery\",\"status\":\"Out\"},{\"firstname\":\"Thomas\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"C8E646D8\",\"shift\":\"Shift 1\",\"lastname\":\"Merrick\",\"status\":\"Out\"},{\"firstname\":\"Marcus\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"45E5059F\",\"shift\":\"Shift 1\",\"lastname\":\"Osborne\",\"status\":\"Out\"},{\"firstname\":\"Rosalee\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"82A8539F\",\"shift\":\"Shift 1\",\"lastname\":\"Patterson\",\"status\":\"Out\"},{\"firstname\":\"Earl\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"618072EA\",\"shift\":\"Shift 1\",\"lastname\":\"Perales\",\"status\":\"Out\"},{\"firstname\":\"Elaine\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"6CA0FF4A\",\"shift\":\"Shift 1\",\"lastname\":\"Pierce\",\"status\":\"Out\"},{\"firstname\":\"Sandra\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"BAD139D6\",\"shift\":\"Shift 1\",\"lastname\":\"Sutherland\",\"status\":\"Out\"},{\"firstname\":\"Kelly\",\"employeetype\":\"Temporary Employee\",\"badgeid\":\"E6386C7C\",\"shift\":\"Shift 1\",\"lastname\":\"Teel\",\"status\":\"Out\"}]";
            jsonExpected = (JsonArray)Jsoner.deserialize(jsonExpectedString);
            
            //Get "Who's In Who's Out" Report (2017-01-01 at 12:00am, no punches
            
            LocalDateTime ts = LocalDateTime.of(2017, 1, 1, 0, 0);
            
            String jsonActualString = reportDAO.getWhosInWhosOut(ts, 1);
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        //Compares expected values
        
        assertNotNull(jsonExpected);
        assertNotNull(jsonActual);
        assertEquals(jsonExpected, jsonActual);
       
    }
    /**
     * Tests the "Who's In Who's Out" report for an invalid department ID.
     * 
     * <p>This test verifies that when an invalid department ID is passed to the report
     * retrieval method, the resulting JSON array is empty, indicating no employees found for 
     * the invalid department input.</p>
     * 
     * @author Cole Stephens
     */
    @Test
    public void testWhosInWhosOutInvalidDepartment() {
        
        JsonArray jsonActual = null;
        
        try {
            
            //Get "Who's In Who's Out" Report (2018-09-05 at 8:00am, negative department
            
            LocalDateTime ts = LocalDateTime.of(2018, 9, 5, 8, 0);
        
            String jsonActualString = reportDAO.getWhosInWhosOut(ts, -1);
            jsonActual = (JsonArray)Jsoner.deserialize(jsonActualString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        //Compares expected values
        
        assertNotNull(jsonActual);
        assertTrue(jsonActual.isEmpty());
    }
    
}
