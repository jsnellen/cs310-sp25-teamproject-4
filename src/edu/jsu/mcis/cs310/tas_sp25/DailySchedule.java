/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Represents a daily work schedule including shift times, lunch breaks, rounding rules, grace periods,
 * and penalties. It also calculates the total shift and lunch durations.
 *
 * <p>This class is part of the time and attendance system.</p>
 * 
 * @author Denzel Stinson
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
     * Constructs a new DailySchedule using key-value pairs provided in a map.
     *
     * @param scheduleInfo A map containing schedule information such as shift/lunch times and policies.
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
     * Gets the shift start time.
     *
     * @return The shift start time.
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }

    /**
     * Gets the shift end time.
     * 
     * @return The shift stop time.
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }

    /**
     * Gets the lunch break start time.
     * 
     * @return The lunch break start time.
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }

    /**
     * Gets the lunch break stop time.
     * 
     * @return The lunch break stop time.
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }

   /**
    * Gets the dock penalty applied for late punches.
    * 
    * @return The dock penalty in minutes.
    */
    public int getDockpenalty() {
        return dockpenalty;
    }

    /**
     * Gets the lunch threshold time.
     * 
     * @returns The lunch threshold in minutes.
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }

    /**
     * Gets the rounding interval in minutes. 
     * 
     * @return The rounding interval.
     */
    public int getRoundInterval() {
        return roundinterval;
    }

    /**
     * Gets the grace period in minutes..
     *
     * @return The grace period.
     */
    public int getGracePeriod() {
        return graceperiod;
    }
    
    /**
     * Gets the duration of the lunch break.
     *
     * @return The lunch duration in minutes.
     */
    public long getLunchduration() {
        return lunchduration;
    }
    
    /**
     * Gets the total duration of the shift.
     *
     * @return The shift duration in minutes.
     */
    public long getShiftduration() {
        return shiftduration;
    }
    
}
