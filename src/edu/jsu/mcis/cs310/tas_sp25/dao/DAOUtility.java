package edu.jsu.mcis.cs310.tas_sp25.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import edu.jsu.mcis.cs310.tas_sp25.DailySchedule;
import edu.jsu.mcis.cs310.tas_sp25.EventType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 * <p>Provides static methods for:
 * <ul>
 *   <li>Calculating total worked minutes and absenteeism</li>
 *   <li>Serializing punch data to JSON</li>
 * </ul>
 * 
 * @author Evan Ranjitkar
 * @author Tanner Thomas
 * @author Cole Stephens
 */
public final class DAOUtility {

    /**
     * Calculates the total number of minutes accrued by an employee within a
     * single day. This method iterates through a list of punches, calculates
     * the time between "clock in" and "clock out" punches, and deducts the
     * lunch break if applicable.
     *
     * @param dailyPunchList A list of Punch objects representing the employee's
     * punches for the day.
     * @param shift The Shift object containing shift rules (e.g., lunch
     * duration and threshold).
     * @return The total number of minutes accrued by the employee for the day.
     */
    //Author: Evan Ranjitkar
    //Editied Method: Tanner Thomas, cStephens
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift) {

        int totalMinutes = 0; //Initialize the total minutes worked

        //Groups a list of punch objects ~cStephens
        Map<LocalDate, List<Punch>> punchesByDay = dailyPunchList.stream()
                .collect(Collectors.groupingBy(p -> p.getAdjustedTimeStamp().toLocalDate()));

        //Iterate through each day's punches, retreving the list of punches for that day. ~Cole Stephens
        for (LocalDate date : punchesByDay.keySet()) {
            List<Punch> dailyPunches = punchesByDay.get(date);
            dailyPunches.sort(Comparator.comparing(p -> p.getAdjustedTimeStamp()));

            int dailyMinutes = 0;
            boolean isClockedIn = false; //Track whether the employee is currently clocked in
            LocalTime clockInTime = null; //Store the time of the most recent clock in punch
            LocalTime clockOutTime = null; // Store the time of the most recent clock out punch

            // Tracking variables for lunch deduction
            LocalTime earliestIn = null;
            LocalTime latestOut = null;
            boolean lunchClockedOut = false;

            DayOfWeek day = date.getDayOfWeek();
            DailySchedule schedule = shift.getDailySchedule(day);

            int lunchDuration = (int) schedule.getLunchduration(); //Duration of the lunch break in minutes
            int lunchThreshold = schedule.getLunchthreshold(); //Minimum minutes required to deduct lunch
            LocalTime lunchStart = schedule.getLunchstart(); // Lunch starts
            LocalTime lunchStop = schedule.getLunchstop(); // Lunch ends

            //Iterate through the list of punches
            // Gets a punch and the punch after it 
            for (int i = 0; i < dailyPunches.size(); i++) {
                Punch punch = dailyPunches.get(i);
                switch (punch.getEventType()) {
                    case CLOCK_IN -> {
                        if (!isClockedIn) {
                            clockInTime = punch.getAdjustedTimeStamp().toLocalTime();
                            isClockedIn = true;

                            // Track earliest clock in
                            if (earliestIn == null || clockInTime.isBefore(earliestIn)) {
                                earliestIn = clockInTime;
                            }
                        }
                    }
                    case CLOCK_OUT -> {
                        if (isClockedIn) {
                            clockOutTime = punch.getAdjustedTimeStamp().toLocalTime();
                            dailyMinutes += ChronoUnit.MINUTES.between(clockInTime, clockOutTime);
                            
                            long rawMinutes = ChronoUnit.MINUTES.between(clockInTime, clockOutTime);
                            System.out.println("Date: " + date);
                            System.out.println("In: " + clockInTime);
                            System.out.println("Out: " + clockOutTime);
                            System.out.println("RawMinutes: " + rawMinutes);

                            isClockedIn = false;
                             
                            // Track latest clock out
                            if (latestOut == null || clockOutTime.isAfter(latestOut)) {
                                latestOut = clockOutTime;
                            }
                            // Checks if the employee clocked out at lunch and clocked back in at 12:30
                            if ((i + 1) < dailyPunches.size()){
                                Punch nextPunch = dailyPunches.get(i + 1);
                                if (nextPunch.getEventType()== EventType.CLOCK_IN){
                                    LocalTime outTime = clockOutTime;
                                    LocalTime inTime = nextPunch.getAdjustedTimeStamp().toLocalTime();
                                    // If employee clocked out after lunch started and clcoks back in at 12:30  
                                    if (!outTime.isAfter(lunchStart) && !inTime.isBefore(lunchStop)){
                                        lunchClockedOut = true; // Employee manually clocked out
                                    }
                                }
                            }
                        }
                    }
                }
            }
                //Deduct lunch if applicable ~Tanner Thomas, Edited by: cStephens
                // The conditions are: The employee worked over the threshold, did not manually clock out for lunch
                // and they need to have clocked in and out for that day (they had to work that day)
                if (dailyMinutes > lunchThreshold && !lunchClockedOut && earliestIn != null && latestOut != null) {
                    if (!earliestIn.isAfter(lunchStart) && latestOut.isAfter(lunchStop)) {
                        dailyMinutes -= lunchDuration;
                        
                        System.out.println("In: " + clockInTime);
                        System.out.println("Out: " + clockOutTime);
                        System.out.println("DailyMinutes: " + dailyMinutes);
                    }
                }

                totalMinutes += dailyMinutes;
                System.out.println("Total Minutes: " + totalMinutes);
            }

            return totalMinutes;
        }
        //Author: Evan Ranjitkar
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        // This is needed or the date and time outputs incorrectly
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        // Create ArrayList object to store punch data
        List<Map<String, String>> jsonData = new ArrayList<>();

        // Iterate through each punch and store its data in a HashMap
        for (Punch punch : dailypunchlist) {
            Map<String, String> punchData = new LinkedHashMap<>();

            // Add punch Data to HashMap
            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", punch.getBadge().getId());
            punchData.put("terminalid", String.valueOf(punch.getTerminalId()));
            punchData.put("punchtype", punch.getEventType().toString());
            punchData.put("adjustmenttype", punch.getAdjustmentType().toString());
            punchData.put("originaltimestamp", punch.getOriginaltimestamp().format(formatter).toUpperCase());
            punchData.put("adjustedtimestamp", punch.getAdjustedTimeStamp().format(formatter).toUpperCase());

            jsonData.add(punchData);
        }

        return Jsoner.serialize(jsonData);

    }

    //Author:Evan Ranjitkar
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {

        int totalMinutesWorked = calculateTotalMinutes(punchlist, s);

        int scheduledMinutesToWork = 0;

        for (int i = 1; i <= 5; i++) {
            DayOfWeek day = DayOfWeek.of(i);
            DailySchedule schedule = s.getDailySchedule(day);

            int dailyMinutes = (int) schedule.getShiftduration() - (int) schedule.getLunchduration();
            scheduledMinutesToWork += dailyMinutes;
//            System.out.println(dailyMinutes);
//            System.out.println(scheduledMinutesToWork);

        }

        double resultPercentage = 100.0 * (scheduledMinutesToWork - totalMinutesWorked) / scheduledMinutesToWork;

//        System.out.println("TotalMinutesWorked: " + totalMinutesWorked);
//        System.out.println("ScheduledMinutesToWork: " + scheduledMinutesToWork);
//        System.out.println("ResultPercentage: " + resultPercentage);
        return BigDecimal.valueOf(resultPercentage);
    }

    /*
    Author: Cole Stephens
    Accepts a list of (already adjusted) Punch objects for an entire pay period, and a Shift object as arguments.
    This method should iterate through this list, copy the data for each punch into a nested data structure, encode 
    this structure as a JSON string, and return this string to the caller. 
     */
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift s) {

        //Gets the punchListAsJSON method
        String punchListJSON = getPunchListAsJSON(punchlist);

        // This is needed or the date and time outputs incorrectly
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        //Variables for the TotalMinutes and absenteeism. 
        int totalMinutes = calculateTotalMinutes(punchlist, s);
        BigDecimal absenteeism = calculateAbsenteeism(punchlist, s);

        //Adds a new Punch Data Set
        JsonArray punchArray = new JsonArray();
        for (Punch punch : punchlist) {
            JsonObject punchData2 = new JsonObject();
            punchData2.put("id", String.valueOf(punch.getId()));
            punchData2.put("badgeid", punch.getBadge().getId());
            punchData2.put("terminalid", String.valueOf(punch.getTerminalId()));
            punchData2.put("punchtype", punch.getEventType().toString());
            punchData2.put("adjustmenttype", punch.getAdjustmentType().toString());
            punchData2.put("originaltimestamp", punch.getOriginaltimestamp().format(formatter).toUpperCase());
            punchData2.put("adjustedtimestamp", punch.getAdjustedTimeStamp().format(formatter).toUpperCase());

            punchArray.add(punchData2);
        }

        //Adds the punchlist method along with the new totalminutes and abensteeism punches.
        HashMap<String, Object> punchTotals = new HashMap<>();

        punchTotals.put("absenteeism", String.format("%.2f%%", absenteeism));
        punchTotals.put("totalminutes", totalMinutes);
        punchTotals.put("punchlist", punchArray);

        //Returns punchTotals
        return Jsoner.serialize(punchTotals);
    }

}
