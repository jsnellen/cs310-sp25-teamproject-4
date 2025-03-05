/**
 *
 * @author evanranjitkar
 * @author Tanner Thomas
 */
package edu.jsu.mcis.cs310.tas_sp25;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Punch {

    // Declaring variables to required for the class
    private int id;
    private int terminalId;
    private Badge badge;
    private EventType eventType;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private PunchAdjustmentType adjustmentType;

    // First Constructor
    public Punch(int terminalId, Badge badge, EventType eventType) {
        this.terminalId = terminalId;
        this.badge = badge;
        this.eventType = eventType;
        this.originalTimeStamp = LocalDateTime.now().withSecond(0).withNano(0);
    }

    // Second Constructor
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originaltimestamp, EventType eventType) {
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.eventType = eventType;
        this.originalTimeStamp = originaltimestamp;
    }

    // Getter Methods
    public int getId() {
        return id;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public Badge getBadge() {
        return badge;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getOriginalTimeStamp() {
        return originalTimeStamp;
    }

    public LocalDateTime getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }

    public void adjust(Shift s) {
        // Store the original timestamp for the punch
        LocalDateTime original = this.originalTimeStamp;
        // Start with no adjustment
        LocalDateTime adjusted = original;
        // Default adjustment is NONE
        PunchAdjustmentType adjustmentType = PunchAdjustmentType.NONE;

        // Shift Parameters
        LocalTime shiftStart = s.getShiftStart();
        LocalTime shiftStop = s.getShiftStop();
        LocalTime lunchStart = s.getLunchStart();
        LocalTime lunchStop = s.getLunchStop();
        int roundInterval = s.getRoundInterval();
        int gracePeriod = s.getGracePeriod();
        int dockPenalty = s.getDockPenalty();

        // Converting timestamps to LocalTime
        LocalTime punchTime = original.toLocalTime();
        DayOfWeek day = original.getDayOfWeek();

        // Skipping adjustments for Time out punches
        if (eventType == EventType.TIME_OUT) {
            this.adjustedTimeStamp = original;
            this.adjustmentType = PunchAdjustmentType.NONE;
            return;
        }

        // Skip Adjustment for Weekend Punches
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            adjusted = adjustToNearestInterval(original, roundInterval);
            adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
            this.adjustedTimeStamp = adjusted;
            this.adjustmentType = adjustmentType;
            return;
        }

        // Shift Start & Stop Adjustment
        if (eventType == EventType.CLOCK_IN && punchTime.isAfter(shiftStart.minusMinutes(roundInterval)) && punchTime.isBefore(shiftStart)) {
            adjusted = original.with(shiftStart);
            adjustmentType = PunchAdjustmentType.SHIFT_START;
        } else if (eventType == EventType.CLOCK_OUT && punchTime.isAfter(shiftStop) && punchTime.isBefore(shiftStop.plusMinutes(roundInterval))) {
            adjusted = original.with(shiftStop);
            adjustmentType = PunchAdjustmentType.SHIFT_STOP;
        } // Lunch Start & Stop Adjustment
        else if (eventType == EventType.CLOCK_OUT && punchTime.isAfter(lunchStart.minusMinutes(roundInterval)) && punchTime.isBefore(lunchStop)) {
            adjusted = original.with(lunchStart);
            adjustmentType = PunchAdjustmentType.LUNCH_START;
        } else if (eventType == EventType.CLOCK_IN && punchTime.isAfter(lunchStart) && punchTime.isBefore(lunchStop.plusMinutes(roundInterval))) {
            adjusted = original.with(lunchStop);
            adjustmentType = PunchAdjustmentType.LUNCH_STOP;
        }

        //Grace Period Adjustment
        if (adjustmentType == PunchAdjustmentType.NONE) {
            if (eventType == EventType.CLOCK_IN && punchTime.isAfter(shiftStart) && punchTime.isBefore(shiftStart.plusMinutes(gracePeriod))) {
                adjusted = original.with(shiftStart);
                adjustmentType = PunchAdjustmentType.SHIFT_START;
            } else if (eventType == EventType.CLOCK_OUT && punchTime.isAfter(shiftStop.minusMinutes(gracePeriod)) && punchTime.isBefore(shiftStop)) {
                adjusted = original.with(shiftStop);
                adjustmentType = PunchAdjustmentType.SHIFT_STOP;
            }
        }
        
        this.adjustedTimeStamp = adjusted;
        this.adjustmentType = adjustmentType;

    }

    // Print methods
    public String printOriginal() {

        // The pringOriginal method shoud return a String in the following format
        //"#badgeid "eventtype" : time stamp in uppercase"
        DateTimeFormatter timeStampFormat = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss");

        // Using Stringbuilder for string concatenation
        StringBuilder stringBuilderResult = new StringBuilder();

        // Using the append method to concat the badgeId, eventType, and timeStamp
        stringBuilderResult.append("#").append(badge.getId()).append(" ")
                .append(eventType.toString()).append(": ")
                .append(originalTimeStamp.format(timeStampFormat).toUpperCase());

        // Returning the formatted string
        return stringBuilderResult.toString();
    }

    public void printAdjusted() {

    }

    //Rounds the punch timestamp to the nearest interval.
    private LocalDateTime adjustToNearestInterval(LocalDateTime time, int interval){
        Timestamp timestamp = Timestamp.valueOf(time);
        LocalDateTime local = timestamp.toLocalDateTime().withSecond(0).withNano(0);
        
        int minutes = local.getMinute();
        int remainder = minutes % interval;
        int adjustment = (remainder < interval / 2) ? -remainder : (interval - remainder);
        
        return local.plusMinutes(adjustment);
        
    }

}
