package edu.jsu.mcis.cs310.tas_sp25;


import static java.lang.Math.abs;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        
        shift_id = (Integer)theMap.get("shiftid");
        round_Interval = (Integer)theMap.get("roundinterval");
        grace_Period = (Integer)theMap.get("graceperiod");
        dock_Penalty = (Integer)theMap.get("dockpenalty");
        lunch_Threshold = (Integer)theMap.get("lunchthreshold");    
        description = (String)theMap.get("description");     
        lunch_Start = (LocalTime)theMap.get("lunchstart");
        lunch_Stop = (LocalTime)theMap.get("lunchstop");    
        shift_Start = (LocalTime)theMap.get("shiftstart");
        shift_Stop = (LocalTime)theMap.get("shiftstop");
        
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

    public LocalTime getShiftstart() {
        return shift_Start;
    }

    public LocalTime getShiftstop() {
        return shift_Stop;
    }

    public LocalTime getLunchstart() {
        return lunch_Start;
    }

    public LocalTime getLunchstop() {
        return lunch_Stop;
    }

    
    
    public int getShiftDuration() {
        
        long minutesInDay = Duration.ofDays(1).toMinutes();
        if (shift_Start.isBefore(shift_Stop)) {
            return (int)(ChronoUnit.MINUTES.between(shift_Start, shift_Stop));
        }
        else {
            return (int)(minutesInDay - ChronoUnit.MINUTES.between(shift_Stop, shift_Start));
        }
        
    }
    
    public int getLunchDuration() {
        
        return abs((int)(ChronoUnit.MINUTES.between(lunch_Start, lunch_Stop)));
    
    }
  
    
    @Override
    public String toString() {
        StringBuilder hello = new StringBuilder();
        hello.append(this.getDescription()).append(": ").append(this.getShiftstart())
        .append(" - ").append(this.getShiftstop()).append(" (").append(this.getShiftDuration())
        .append(" minutes); Lunch: ").append(this.getLunchstart()).append(" - ")
        .append(this.getLunchstop()).append(" (").append(this.getLunchDuration()).append(" minutes)");
        
        String totalStr = hello.toString();
        
        return totalStr;
    }
    
}
