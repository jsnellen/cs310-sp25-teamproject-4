package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.util.HashMap;
import java.time.Duration;

/**
 * The `Shift` class represents a work shift with its associated properties such as shift ID, description,
 * start and stop times, rounding interval, grace period, dock penalty, lunch start and stop times, and lunch threshold.
 * 
 * This class is designed to be used in a time and attendance system to manage and calculate shift-related data.
 *
 * @author denzel
 * @author mahin
 */
public class Shift {

    // Declare a private field to store the a unique identifier
    private int shift_id;
    private int round_Interval;
    private int grace_Period;
    private int dock_Penalty;
    private int lunch_Threshold;
    private String description;
    private LocalTime shift_Start;
    private LocalTime shift_Stop;
    private LocalTime lunch_Start;
    private LocalTime lunch_Stop;

     /**
     * Constructor for the `Shift` class.
     * Initializes the shift properties using a HashMap containing the shift details.
     *
     * @param theMap A HashMap containing key-value pairs for shift properties.
     */
    public Shift(HashMap<String, Object> theMap) {

         // Extract and assign the variables from the HashMap
        shift_id = (Integer) theMap.get("shiftid");
        round_Interval = (Integer) theMap.get("roundinterval");
        grace_Period = (Integer) theMap.get("graceperiod");
        dock_Penalty = (Integer) theMap.get("dockpenalty");
        lunch_Threshold = (Integer) theMap.get("lunchthreshold");
        description = (String) theMap.get("description");
        lunch_Start = (LocalTime) theMap.get("lunchstart");
        lunch_Stop = (LocalTime) theMap.get("lunchstop");
        shift_Start = (LocalTime) theMap.get("shiftstart");
        shift_Stop = (LocalTime) theMap.get("shiftstop");

    }

    //Getters methods for accessing shift properties
    
     /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public int getShiftid() {
        return shift_id;
    }

    /**
     * Returns the rounding interval for clock-in/out times.
     *
     * @return The rounding interval in minutes.
     */
    public int getRoundInterval() {
        return round_Interval;
    }

    /**
     * Returns the grace period for clocking in/out.
     *
     * @return The grace period in minutes.
     */
    public int getGracePeriod() {
        return grace_Period;
    }

    /**
     * Returns the dock penalty for clocking in/out late.
     *
     * @return The dock penalty in minutes.
     */
    public int getDockPenalty() {
        return dock_Penalty;
    }

    /**
     * Returns the lunch threshold.
     *
     * @return The lunch threshold in minutes.
     */
    public int getLunchThreshold() {
        return lunch_Threshold;
    }

    /**
     * Returns the shift description.
     *
     * @return The shift description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the lunch break start time.
     *
     * @return The lunch break start time.
     */
    public LocalTime getLunchStart() {
        return lunch_Start;
    }

    /**
     * Returns the lunch break stop time.
     *
     * @return The lunch break stop time.
     */
    public LocalTime getLunchStop() {
        return lunch_Stop;
    }

    /**
     * Returns the shift start time.
     *
     * @return The shift start time.
     */
    public LocalTime getShiftStart() {
        return shift_Start;
    }

    /**
     * Returns the shift stop time.
     *
     * @return The shift stop time.
     */
    public LocalTime getShiftStop() {
        return shift_Stop;
    }

    /**
     * Calculates the total duration of the shift in minutes.
     * If the shift spans midnight, it adjusts the calculation accordingly.
     *
     * @return The total shift duration in minutes.
     */
    public int getShiftDuration() {
        if (shift_Start.isBefore(shift_Stop)) {
            return (int) Duration.between(shift_Start, shift_Stop).toMinutes();
        } else {
            return (int) (Duration.between(shift_Start, LocalTime.MAX).toMinutes()
                    + Duration.between(LocalTime.MIN, shift_Stop).toMinutes());
        }
    }

    /**
     * Calculates the total duration of the lunch break in minutes.
     *
     * @return The total lunch break duration in minutes.
     */
    public int getLunchDuration() {
        return (int) Duration.between(lunch_Start, lunch_Stop).toMinutes();
    }

    /**
     * Generates a formatted string representation of the shift.
     * Includes shift description, start/stop times, total shift duration, and lunch break details.
     *
     * @return A formatted string representing the shift.
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description,
                shift_Start, shift_Stop, getShiftDuration(),
                lunch_Start, lunch_Stop, getLunchDuration());
    }

    /**
     * Compares this Shift object with another based on the shift ID. ID.
     *
     * @param ob The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (ob == null || getClass() != ob.getClass()) {
            return false;
        }
        Shift shift = (Shift) ob;
        return shift_id == shift.shift_id;
    }

    /**
     * Generates a hash code for the Shift object based on the shift ID.
     *
     * @return A hash code for the Shift object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(shift_id);
    }
}

