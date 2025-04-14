package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.util.HashMap;
import java.time.Duration;
import java.time.DayOfWeek;

/**
 *
 * Represents a work shift, including start/stop times, lunch breaks,
 * rounding rules, and daily schedule overrides.
 *
 * <p>Each {@code Shift} contains a default schedule and optional daily overrides for 
 * each weekday. It supports cloning and comparison based on shift ID.</p>
 * 
 * @author denzel
 * @author mahin
 * @author Tanner Thomas
 */
public class Shift {

    // Declare a private field to store the a unique identifier
    private int shift_id;
    private String description;
    private DailySchedule defaultSchedule;
    private HashMap<Integer, DailySchedule> dailySchedules;

    public Shift(HashMap<String, Object> theMap, DailySchedule schedule) {

        // Extract and assign the variables from the HashMap
        this.shift_id = (Integer) theMap.get("shiftid");
        this.description = (String) theMap.get("description");
        this.defaultSchedule = schedule;

        dailySchedules = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            dailySchedules.put(i, schedule);
        }
    }
    // Creates a copy of an existing Shift object, default, and daily schedules
    // This is used to apply overrides so the original shift remains unchanged
    public Shift(Shift original){
        this.shift_id = original.shift_id;
        this.description = original.description;
        this.defaultSchedule = original.defaultSchedule;
        this.dailySchedules = new HashMap<>();
        
        // Copy each daily schedule from the original shift into the new shift dailySchedules Map
        for (int i =1; i <=5; i++){
            this.dailySchedules.put(i, original.getDailySchedule(i));
        }
    }

    //Getters methods for accessing shift properties    
    public int getShiftid() {
        return shift_id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getLunchStart() {
        return defaultSchedule.getLunchstart();
    }

    public LocalTime getLunchStop() {
        return defaultSchedule.getLunchstop();
    }

    public LocalTime getShiftStart() {
        return defaultSchedule.getShiftstart();
    }

    public LocalTime getShiftStop() {
        return defaultSchedule.getShiftstop();
    }

    public int getShiftDuration() {
        return (int) defaultSchedule.getShiftduration();
    }

    public int getLunchDuration() {
        return (int) defaultSchedule.getLunchduration();
    }

    public int getRoundInterval() {
        return defaultSchedule.getRoundInterval();
    }

    public int getGracePeriod() {
        return (int) defaultSchedule.getGracePeriod();
    }

    public int getDockPenalty() {
        return (int) defaultSchedule.getDockpenalty();
    }

    public int getLunchThreshold() {
        return (int) defaultSchedule.getLunchthreshold();
    }

    public DailySchedule getDefaultSchedule() {
        return defaultSchedule;
    }

    public DailySchedule getDailySchedule(int dayOfWeek) {
        return dailySchedules.getOrDefault(dayOfWeek, defaultSchedule);
    }
    
    // Overloaded method DayofWeek
    public DailySchedule getDailySchedule(DayOfWeek day) {
        return dailySchedules.getOrDefault(day.getValue(), defaultSchedule);
    }
    
    // Setter to apply schedule overrides
    public void setDailySchedule(DayOfWeek day, DailySchedule schedule) {
        dailySchedules.put(day.getValue(), schedule);
    }

    /**
     * Generates a formatted string representation of the shift. Includes shift
     * description, start/stop times, total shift duration, and lunch break
     * details.
     *
     * @return A formatted string representing the shift.
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description,
                getShiftStart(), getShiftStop(), getShiftDuration(),
                getLunchStart(), getLunchStop(), getLunchDuration());
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
