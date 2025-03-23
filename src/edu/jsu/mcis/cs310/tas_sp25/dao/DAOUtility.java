package edu.jsu.mcis.cs310.tas_sp25.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

    /**
     * Calculates the total number of minutes accrued by an employee within a single day.
     * This method iterates through a list of punches, calculates the time between "clock in"
     * and "clock out" punches, and deducts the lunch break if applicable.
     *
     * @param dailyPunchList A list of Punch objects representing the employee's punches for the day.
     * @param shift The Shift object containing shift rules (e.g., lunch duration and threshold).
     * @return The total number of minutes accrued by the employee for the day.
     */
    //Author: Evan Ranjitkar
    //Editied Method: Tanner Thomas, Cole Stephens
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift) {
    
        int totalMinutes = 0; //Initialize the total minutes worked
        
        //Groups a list of punch objects ~Cole Stephens
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
        
            int lunchDuration = shift.getLunchDuration(); //Duration of the lunch break in minutes
            int lunchThreshold = shift.getLunchThreshold(); //Minimum minutes required to deduct lunch
            LocalTime lunchStart = shift.getLunchStart(); // Lunch starts
            LocalTime lunchStop = shift.getLunchStop(); // Lunch ends
            
            //Iterate through the list of punches
            for (Punch punch : dailyPunches) {
                switch (punch.getEventType()) {
                    case CLOCK_IN -> {
                        if (!isClockedIn) {
                            clockInTime = punch.getAdjustedTimeStamp().toLocalTime();
                            isClockedIn = true;
                        }
                    }
                    case CLOCK_OUT -> {
                        if (isClockedIn) {
                            clockOutTime = punch.getAdjustedTimeStamp().toLocalTime();
                            dailyMinutes += ChronoUnit.MINUTES.between(clockInTime, clockOutTime);
                            isClockedIn = false;
                        }
                    }
                }
            }
            
            //Deduct lunch if applicable ~Tanner Thomas, Edited by: Cole Stephens
            if (dailyMinutes > lunchThreshold && clockInTime != null && clockOutTime != null) {
                if (clockInTime.isBefore(lunchStart) && clockOutTime.isAfter(lunchStop)) {
                    dailyMinutes -= lunchDuration;
                }
            }    
            
            totalMinutes += dailyMinutes;
        }
        
        return totalMinutes;
    }    
            
    //Author: Evan Ranjitkar
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
        // This is needed or the date and time outputs incorrectly
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        // Create ArrayList object to store punch data
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        
        // Iterate through each punch and store its data in a HashMap
        for (Punch punch : dailypunchlist){
            HashMap<String, String> punchData = new HashMap<>();
            
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
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        
        int totalMinutesWorked = calculateTotalMinutes(punchlist, s);
        
        int scheduledMinutesToWork = 0;

        scheduledMinutesToWork += 5 * (s.getShiftDuration() - s.getLunchDuration());

        double resultPercentage = 100.0 * (scheduledMinutesToWork - totalMinutesWorked) / scheduledMinutesToWork;
        
        return BigDecimal.valueOf(resultPercentage);
    }
    
    /*
    Author: Cole Stephens
    Accepts a list of (already adjusted) Punch objects for an entire pay period, and a Shift object as arguments.
    This method should iterate through this list, copy the data for each punch into a nested data structure, encode 
    this structure as a JSON string, and return this string to the caller. 
    */
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift) {
        
        //Gets the punchListAsJSON method
        String punchListJSON = getPunchListAsJSON(punchlist);
        
        // This is needed or the date and time outputs incorrectly
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        //Variables for the TotalMinutes and absenteeism. 
        int totalMinutes = calculateTotalMinutes(punchlist, shift);
        BigDecimal absenteeism = calculateAbsenteeism(punchlist, shift).setScale(2, BigDecimal.ROUND_HALF_UP);
        
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
        
        //Serializes the new Data to JSON
        String serializedPunchArray = punchArray.toString();
        
        //Adds the punchlist method along with the new totalminutes and abensteeism punches.
        HashMap<String, String> punchTotals = new HashMap<>();
        
        punchTotals.put("absenteeism", absenteeism.toString() + "%");
        punchTotals.put("totalminutes", String.valueOf(totalMinutes));
        punchTotals.put("punchlist", serializedPunchArray);
        
        //Returns punchTotals
        return Jsoner.serialize(punchTotals);
    }
    
}