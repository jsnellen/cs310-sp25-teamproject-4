package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.util.HashMap;
import java.time.Duration;

/**
 *
 * @author denzel
 * @author mahin
 */
public class Shift {

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

    public Shift(HashMap<String, Object> theMap) {

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

    public int getShiftid() {
        return shift_id;
    }

    public int getRoundInterval() {
        return round_Interval;
    }

    public int getGracePeriod() {
        return grace_Period;
    }

    public int getDockPenalty() {
        return dock_Penalty;
    }

    public int getLunchThreshold() {
        return lunch_Threshold;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getLunchStart() {
        return lunch_Start;
    }

    public LocalTime getLunchStop() {
        return lunch_Stop;
    }

    public LocalTime getShiftStart() {
        return shift_Start;
    }

    public LocalTime getShiftStop() {
        return shift_Stop;
    }

    public int getShiftDuration() {
        if (shift_Start.isBefore(shift_Stop)) {
            return (int) Duration.between(shift_Start, shift_Stop).toMinutes();
        } else {
            return (int) (Duration.between(shift_Start, LocalTime.MAX).toMinutes()
                    + Duration.between(LocalTime.MIN, shift_Stop).toMinutes());
        }
    }

    public int getLunchDuration() {
        return (int) Duration.between(lunch_Start, lunch_Stop).toMinutes();
    }

    /**
     * Returns a string representation of the Shift object.
     *
     * @return A string representation of the shift.
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description,
                shift_Start, shift_Stop, getShiftDuration(),
                lunch_Start, lunch_Stop, getLunchDuration());
    }

    /**
     * Compares this Shift object with another based on the shift ID.
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

