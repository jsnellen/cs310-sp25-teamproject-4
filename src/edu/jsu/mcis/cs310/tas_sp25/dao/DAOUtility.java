package edu.jsu.mcis.cs310.tas_sp25.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


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
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift) {
    
        int totalMinutes = 0; //Initialize the total minutes worked
        boolean isClockedIn = false; //Track whether the employee is currently clocked in
        
        LocalTime clockInTime = null; //Store the time of the most recent clock in punch
        LocalTime clockOutTime = null; // Store the time of the most recent clock out punch
        
        int lunchDuration = shift.getLunchDuration(); //Duration of the lunch break in minutes
        int lunchThreshold = shift.getLunchThreshold(); //Minimum minutes required to deduct lunch
        
        LocalTime lunchStart = shift.getLunchStart(); // Lunch starts
        LocalTime lunchStop = shift.getLunchStop(); // Lunch ends

        
        //Iterate through the list of punches
        for (Punch punch : dailyPunchList) {
            switch (punch.getEventType()) {
                case CLOCK_IN -> {
                    //If the employee is not already clocked in, record the clock in time
                    if (!isClockedIn) {
                        clockInTime = punch.getAdjustedTimeStamp().toLocalTime(); //Get adjusted time
                        isClockedIn = true; //Marks employee as clocked in
                    }
                }
                case CLOCK_OUT -> {
                    //If the employee is clocked in, calculate the time worked since the last clock in
                    if (isClockedIn) {
                        clockOutTime = punch.getAdjustedTimeStamp().toLocalTime(); // Get adjusted clock out time
                        
                        // Calculates minutes worked between clock in and clock out
                        totalMinutes += ChronoUnit.MINUTES.between(clockInTime, clockOutTime);
                        
                        isClockedIn = false; //Marks employee as clocked out
                    }
                }
            }
        }
        
        //Deduct lunch break if the total minutes worked exceed the lunch threshold
        if (totalMinutes > lunchThreshold) {
            // Ensures clock in and clock out times exist before checking for lunch deduction
            if (clockInTime != null && clockOutTime != null){
                // Deduct lunch only if the employee worked through lunch
                if (clockInTime.isBefore(lunchStart)&& clockOutTime.isAfter(lunchStop)){
                    totalMinutes -= lunchDuration;
                }
            }
        }   
    return totalMinutes;
    }
    
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
    
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        
        int totalMinutesWorked = calculateTotalMinutes(punchlist, s);
        
        int scheduledMinutesToWork = 0;
        
        ArrayList<LocalDate> dateList = new ArrayList<>();
         
        for (Punch punch: punchlist){
            
            LocalDate date = punch.getOriginalTimeStamp().toLocalDate();
            
            System.out.println(date);
            
            if (!dateList.contains(date)) {
                
                dateList.add(date); 
                
                scheduledMinutesToWork += s.getShiftDuration() - s.getLunchDuration();
                
                System.out.println(s.getShiftDuration());
                
            }
        }
        
        System.out.println(scheduledMinutesToWork);
        System.out.println(totalMinutesWorked);
        double resultPercentage = 100.0 * (scheduledMinutesToWork - totalMinutesWorked) / scheduledMinutesToWork;
        
        System.out.println(resultPercentage);
        return BigDecimal.valueOf(resultPercentage);
    }
    
    
}