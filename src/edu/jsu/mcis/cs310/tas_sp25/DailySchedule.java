/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 *The `DailySchedule` class represents a daily work schedule with its associated properties such as shift start/stop times,
 * lunch start/stop times, rounding interval, grace period, dock penalty, lunch threshold, and calculated durations for
 * the shift and lunch break.
 *
 * This class is designed to store and provide access to daily schedule details for use in a time and attendance system.
 * 
 * @author denzel
 */
public class DailySchedule {
    
    //Fields to store schedule-related properties
    private final LocalTime shiftstart; //Start time of teh shift
    private final LocalTime shiftstop; //Stop time of the shift
    private final LocalTime lunchstart; //Start time of the lunch break
    private final LocalTime lunchstop; //Stop time of the lunch break
    private final int roundinterval; //Interval for rounding clock-in/out times
    private final int graceperiod; //Grace period allowed for clocking in/out
    private final int dockpenalty; //Penalty for clocking in/out late
    private final int lunchthreshold; //Threshold for requiring a lunch break
    private final long lunchduration; //Duration of the lunch break in minutes
    private final long shiftduration; //Duration of the shift in minutes
    
    /**
     * Constructor for the `DailySchedule` class.
     * Initializes the schedule properties using a Map containing the schedule details.
     *
     * @param scheduleInfo A Map containing key-value pairs for schedule properties.
     */
    public DailySchedule(Map<String, String> scheduleInfo) {
    
        // Parse and assign values from the Map to the corresponding fields
        this.shiftstart = LocalTime.parse(scheduleInfo.get("shiftstart"));
        this.shiftstop = LocalTime.parse(scheduleInfo.get("shiftstop"));
        this.lunchstart = LocalTime.parse(scheduleInfo.get("lunchstart"));
        this.lunchstop = LocalTime.parse(scheduleInfo.get("lunchstop"));
        this.roundinterval = Integer.parseInt(scheduleInfo.get("roundinterval"));
        this.graceperiod = Integer.parseInt(scheduleInfo.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(scheduleInfo.get("dockpenalty"));
        this.lunchthreshold = Integer.parseInt(scheduleInfo.get("lunchthreshold"));
        
        //Calculate the duration of the lunch break and shift
        this.lunchduration = ChronoUnit.MINUTES.between(lunchstart, lunchstop);
        this.shiftduration = ChronoUnit.MINUTES.between(shiftstart, shiftstop);
    }
    
    //Getters methods for accessing shift properties
    
    /**
     * Returns the shift start time.
     *
     * @return The shift start time.
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }

    /**
     * Returns the shift stop time.
     * 
     * @return The shift stop time.
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }

    /**
     * Returns the lunch start break time.
     * 
     * @return The lunch start break time.
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }

    /**
     * Returns the lunch break stop time.
     * 
     * @return The lunch break stop time.
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }

   /**
    * Returns the dock penalty for clocking in/out late.
    * 
    * @return the dock penalty in minutes.
    */
    public int getDockpenalty() {
        return dockpenalty;
    }

    /**
     * Returns the lunch threshold.
     * 
     * @returns The lunch threshold in minutes.
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }

    /**
     * Returns the round interval 
     * 
     * @return The rounding interval in minutes.
     */
    public int getRoundInterval() {
        return roundinterval;
    }

    /**
     * Returns the grace period for clocking in/out.
     *
     * @return The grace period in minutes.
     */
    public int getGracePeriod() {
        return graceperiod;
    }
    
    /**
     * Returns the duration of the lunch break.
     *
     * @return The lunch break duration in minutes.
     */
    public long getLunchduration() {
        return lunchduration;
    }
    
    /**
     * Returns the duration of the shift.
     *
     * @return The shift duration in minutes.
     */
    public long getShiftduration() {
        return shiftduration;
    }
    
}
