package edu.jsu.mcis.cs310.tas_sp25.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp25.Punch;
import edu.jsu.mcis.cs310.tas_sp25.Shift;


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
        int lunchDuration = shift.getLunchDuration(); //Duration of the lunch break in minutes
        int lunchThreshold = shift.getLunchThreshold(); //Minimum minutes required to deduct lunch
        
        //Iteraye through the list of punchs
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
                        LocalTime clockOutTime = punch.getAdjustedTimeStamp().toLocalTime(); //Get adjusted time
                        long minutesWorked = ChronoUnit.MINUTES.between(clockInTime, clockOutTime);
                        totalMinutes += (int) minutesWorked; //Add duration to total minutes
                        isClockedIn = false; //Marks employee ascloked out
                    }
                }
                case TIME_OUT -> //Ignore time between CLOCK_IN and TIME_OUT
                    isClockedIn = false;
            }
        }
        
        //Deduct lunch break if the total minutes worked exceed the lunch threshold
        if (totalMinutes > lunchThreshold) {
            totalMinutes -= lunchDuration; //Subtract the lunch duration from the total
        }
        
        //Return the total minutes worked for the day
        return totalMinutes;
    }
}