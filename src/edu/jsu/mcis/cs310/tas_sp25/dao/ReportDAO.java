package edu.jsu.mcis.cs310.tas_sp25.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp25.Badge;
import edu.jsu.mcis.cs310.tas_sp25.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

/**
 * DAO for generating various JSON-based employee reports, including badge
 * summaries, hours summaries, and real-time attendance status.
 *
 * <p>
 * Provides methods to retrieve data such as badge summaries, hours worked, and
 * real-time attendance status ("Who's In/Out"). Results are returned as JSON
 * strings</p>
 *
 * @author evanranjitkar
 */
public class ReportDAO {

    private final DAOFactory daoFactory;

    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static final String Query_GET_BADGE_BY_ID = "SELECT badge.id as badgeId, badge.description AS employeeName, "
            + "department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "WHERE departmentid = ? "
            + "ORDER BY badge.description";

    private static final String Query_GET_BADGE_BY_NULL = "SELECT badge.id as badgeId, badge.description AS employeeName,"
            + " department.description AS departmentName, employeetype.description AS employeeType "
            + "FROM badge JOIN employee ON badge.id = employee.badgeid "
            + "JOIN department ON employee.departmentid = department.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "ORDER BY badge.description";

    private static final String Query_GET_HOURS_WITH_ID_AND_TYPE = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift, employee.badgeid AS badgeId"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE departmentid = ? AND ((DATE(timestamp) BETWEEN ? AND ?) AND (employeetypeid = ?))"
            + " ORDER BY lastname, firstname, middlename";

    private static final String Query_GET_HOURS_WITH_ID = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift, employee.badgeid AS badgeId"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE departmentid = ? AND (DATE(timestamp) BETWEEN ? AND ?)"
            + " ORDER BY lastname, firstname, middlename";

    private static final String Query_GET_HOURS_WITH_TYPE = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift, employee.badgeid AS badgeId"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE (DATE(timestamp) BETWEEN ? AND ?) AND (employeetypeid = ?)"
            + " ORDER BY lastname, firstname, middlename";

    private static final String Query_GET_HOURS_ALL = "SELECT employee.firstname AS firstName, employee.middlename AS middleName"
            + ", employee.lastname AS lastName, department.description AS departmentName,"
            + " employeetype.description AS employeeType, shift.description AS assignedShift, employee.badgeid AS badgeId"
            + " FROM employee JOIN department ON employee.departmentid = department.id"
            + " JOIN employeetype ON employee.employeetypeid = employeetype.id"
            + " JOIN shift ON employee.shiftid = shift.id"
            + " JOIN event ON employee.badgeid = event.badgeid"
            + " WHERE DATE(timestamp) BETWEEN ? AND ?"
            + " ORDER BY lastname, firstname, middlename";

    private static final String QUERY_WHOS_IN_WHOS_OUT = "SELECT employee.badgeid AS badgeId, badge.description AS employeeName, "
            + "employee.firstname AS firstname, employee.lastname AS lastname, employeetype.description AS employeeType,"
            + "shift.description AS shift, event.timestamp AS arrived, eventtype.description AS eventType "
            + "FROM employee "
            + "JOIN badge ON employee.badgeid = badge.id "
            + "JOIN employeetype ON employee.employeetypeid = employeetype.id "
            + "JOIN shift ON employee.shiftid = shift.id "
            + "LEFT JOIN event ON badge.id = event.badgeid AND event.timestamp <= ? "
            + "LEFT JOIN eventtype ON event.eventtypeid = eventtype.id "
            + "WHERE (? IS NULL OR employee.departmentid = ?) "
            + "ORDER BY employee.lastname, employee.firstname, event.timestamp DESC";

    /**
     * Retrieves a summary of employee badge information for a specified
     * department.
     *
     * @param departmentId Uses the ID of the department to filter employees to
     * either include departments or by null.
     * @return A JSON-formatted string representing the employee badge summary.
     *
     * @author Evan Ranjitkar
     */
    public String getBadgeSummary(Integer departmentId) {

        JsonArray resultArray = new JsonArray();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId != null) {

                    ps = conn.prepareStatement(Query_GET_BADGE_BY_ID);
                    ps.setInt(1, departmentId);

                } else {
                    ps = conn.prepareStatement(Query_GET_BADGE_BY_NULL);
                }

                // Executing the PreparedStatement
                boolean hasResults = ps.execute();

                // If query has results, then retrieving the data
                if (hasResults) {

                    // Getting result set and storing it in the ResultSet variable
                    rs = ps.getResultSet();

                    while (rs.next()) {

                        JsonObject result = new JsonObject();

                        result.put("badgeid", rs.getString("badgeId"));
                        result.put("name", rs.getString("employeeName"));
                        result.put("department", rs.getString("departmentName"));
                        result.put("type", rs.getString("employeeType"));

                        resultArray.add(result);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return Jsoner.serialize(resultArray);
    }

    public String getHoursSummary(LocalDate date, Integer departmentId, EmployeeType employeeType) {

        JsonArray resultArray = new JsonArray();

        PreparedStatement ps = null;
        ResultSet rs = null;

        // Getting the previous or same Sunday from the given date
        LocalDate begin = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        // From that Sunday, getting the following Saturday (end of the same week)
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        // ArrayList which will contain the employees whose hours have already been calculated
        ArrayList<String> badgeList = new ArrayList();

        try {

            Connection conn = daoFactory.getConnection();

            // Getting a BadgeDAO instance from the daoFactory
            BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

            // Getting a BadgeDAO instance from the daoFactory
            PunchDAO punchDAO = daoFactory.getPunchDAO();

            // Getting a BadgeDAO instance from the daoFactory
            ShiftDAO shiftDAO = daoFactory.getShiftDAO();

            if (conn.isValid(0)) {

                // If else statement to determine which query to run
                if (departmentId != null && employeeType != null) {

                    ps = conn.prepareStatement(Query_GET_HOURS_WITH_ID_AND_TYPE);
                    ps.setInt(1, departmentId);
                    ps.setDate(2, java.sql.Date.valueOf(begin));
                    ps.setDate(3, java.sql.Date.valueOf(end));
                    ps.setInt(4, employeeType.ordinal());

                } else if (departmentId != null) {

                    ps = conn.prepareStatement(Query_GET_HOURS_WITH_ID);
                    ps.setInt(1, departmentId);
                    ps.setDate(2, java.sql.Date.valueOf(begin));
                    ps.setDate(3, java.sql.Date.valueOf(end));

                } else if (employeeType != null) {

                    ps = conn.prepareStatement(Query_GET_HOURS_WITH_TYPE);
                    ps.setDate(1, java.sql.Date.valueOf(begin));
                    ps.setDate(2, java.sql.Date.valueOf(end));
                    ps.setInt(3, employeeType.ordinal());

                } else {
                    ps = conn.prepareStatement(Query_GET_HOURS_ALL);
                    ps.setDate(1, java.sql.Date.valueOf(begin));
                    ps.setDate(2, java.sql.Date.valueOf(end));
                }

                // Executing the PreparedStatement
                boolean hasResults = ps.execute();

                // If query has results, then retrieving the data
                if (hasResults) {

                    // Getting result set and storing it in the ResultSet variable
                    rs = ps.getResultSet();

                    // Looping through the result set
                    while (rs.next()) {

                        // Getting the badgeid from the result set
                        String badgeId = rs.getString("badgeId");

                        // if the ArrayList badgeList does not contain the badgeId
                        if (!badgeList.contains(badgeId)) {
                            
                            // Initializing a JsonObject to store the data
                            JsonObject result = new JsonObject();

                            // Getting the values from the result set and putting it in the JsonObject
                            result.put("employeetype", rs.getString("employeetype"));
                            result.put("shift", rs.getString("assignedShift"));
                            result.put("name", (rs.getString("lastname") + ", " + rs.getString("firstname") + " " + rs.getString("middlename")));
                            result.put("middlename", rs.getString("middlename"));

                            // Using the BadgeDOA's find method the find the badge with the badgeid
                            Badge badge = badgeDAO.find(rs.getString("badgeId"));
                            
                            //Using the PunchDAO's list method the get the ArrayList of Punches for the date interval
                            ArrayList<Punch> punchList = punchDAO.list(badge, begin, end);

                            // Using the ShiftDAO's find method to find the shift for the badge and date
                            Shift shift = shiftDAO.find(badge, date);

                            // Adjusting all the punches in the punch ArrayList
                            for (Punch punch : punchList) {
                                punch.adjust(shift);
                            }

                            // Calculating the minutes worked, the regular hours and overtime hours 
                            BigDecimal workedMinutes = BigDecimal.valueOf(DAOUtility.calculateTotalMinutes(punchList, shift));

                            BigDecimal workedHours = workedMinutes.divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

                            BigDecimal regularHours = BigDecimal.valueOf((shift.getShiftDuration() / 60) * 5);

                            BigDecimal overtimeHours = BigDecimal.valueOf(0);

                            if (workedHours.compareTo(regularHours) == 1) {

                                overtimeHours = workedHours.subtract(regularHours);
                                workedHours = regularHours;
                            }

                            // Using the setScale method for two decimal places
                            workedHours = workedHours.setScale(2);
                            overtimeHours = overtimeHours.setScale(2);
                            
                            result.put("overtime", overtimeHours.toString());
                            result.put("department", rs.getString("departmentName"));
                            result.put("regular", workedHours.toString());
                            result.put("lastname", rs.getString("lastName"));

                            // Adding the JsonObject to the JsonArray
                            resultArray.add(result);
                            
                            // Adding the current badgeId to the badgeList
                            badgeList.add(badgeId);

                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println(Jsoner.serialize(resultArray));
        return Jsoner.serialize(resultArray);
    }

    /**
     * Generates a JSON report listing which employees are clocked ("In") and
     * which are clocked ("Out") at a specific date and time. Optionally filters
     * by department if a department ID is provided.
     *
     * @param dateTime The date and time to check employee statuses
     * @param departmentId The department ID to filter by (or null for all
     * departments)
     * @return A JSON string representing the report data
     *
     * @author Cole Stephens
     */

    public String getWhosInWhosOut(LocalDateTime dateTime, Integer departmentId) {

        JsonArray resultArray = new JsonArray(); //Result array to hold the final JSON objects
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            //Checks if the connection is valid before proceeding
            if (conn.isValid(0)) {

                //Prepares the query before passing the report date/time and department filter
                ps = conn.prepareStatement(QUERY_WHOS_IN_WHOS_OUT);
                ps.setTimestamp(1, Timestamp.valueOf(dateTime));
                ps.setObject(2, departmentId);
                ps.setObject(3, departmentId);

                rs = ps.executeQuery();

                //Uses a map to keep track of each employee by the badge ID while avoiding duplicate records
                HashMap<String, JsonObject> employeeMap = new HashMap<>();

                //Processes each row from the result set
                while (rs.next()) {

                    String badgeId = rs.getString("badgeId");

                    //Only processes the first occurence off each employee based on the latest event timestamp
                    if (!employeeMap.containsKey(badgeId)) {
                        JsonObject obj = new JsonObject();
                        obj.put("firstname", rs.getString("firstname"));
                        obj.put("lastname", rs.getString("lastname"));
                        obj.put("badgeid", badgeId);
                        obj.put("employeetype", rs.getString("employeetype"));
                        obj.put("shift", rs.getString("shift"));

                        //Determines employee's status based on their event type
                        String eventType = rs.getString("eventType");
                        String status = determineInOut(eventType);
                        obj.put("status", status);

                        //If employee is currently "In". formats and stores their original arrival time
                        Timestamp arrived = rs.getTimestamp("arrived");
                        if (arrived != null && "In".equals(status)) {
                            LocalDateTime localArrival = arrived.toLocalDateTime();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
                            String formattedArrival = localArrival.format(formatter);

                            //Formats the arrival time and manually capitalizes the day abbreviation
                            String finalArrival = formattedArrival.substring(0, 3).toUpperCase() + formattedArrival.substring(3);

                            obj.put("arrived", finalArrival);
                        }

                        //Adds employee record to the map
                        employeeMap.put(badgeId, obj);
                    }
                }

                //Separates employee's into "In" and "Out" lists
                ArrayList<JsonObject> inList = new ArrayList<>();
                ArrayList<JsonObject> outList = new ArrayList<>();

                for (JsonObject obj : employeeMap.values()) {
                    if ("In".equals(obj.get("status"))) {
                        inList.add(obj);
                    } else {
                        outList.add(obj);
                    }
                }

                //Defines the sort order: by employeetype, lastname, then firstname
                Comparator<JsonObject> employeeComparator = Comparator
                        .comparing((JsonObject o) -> (String) o.get("employeetype"))
                        .thenComparing((JsonObject o) -> (String) o.get("lastname"))
                        .thenComparing((JsonObject o) -> (String) o.get("firstname"));

                //Sorts both lists accordingly
                inList.sort(employeeComparator);
                outList.sort(employeeComparator);

                //Combines the sorted lists into the final result array
                resultArray.addAll(inList);
                resultArray.addAll(outList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ps != null) try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Returns the serialized JSON string
        return Jsoner.serialize(resultArray);
    }

    /**
     * Helper method to determine employee "In" or "Out" status based on event
     * type.
     *
     * @param eventType The event type string from the database
     * @return "In" if event type is "Clock In", otherwise "Out"
     *
     * @author Cole Stephens
     */
    private String determineInOut(String eventType) {
        return "Clock In".equals(eventType) ? "In" : "Out";
    }

}
