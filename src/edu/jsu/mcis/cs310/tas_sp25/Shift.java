package edu.jsu.mcis.cs310.tas_sp25;


import static java.lang.Math.abs;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author denzel
 * 
 */
public class Shift {
    
    private int shiftid;
    private String desc;
    private LocalTime shiftstart;
    private LocalTime shiftstop;
    private int roundInterval;
    private int gracePeriod;
    private int dockPenalty;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private int lunchThreshold;
    
    public Shift(HashMap<String, Object> theMap) {
        
        shiftid = (Integer)theMap.get("shiftid");
        roundInterval = (Integer)theMap.get("roundinterval");
        gracePeriod = (Integer)theMap.get("graceperiod");
        dockPenalty = (Integer)theMap.get("dockpenalty");
        lunchThreshold = (Integer)theMap.get("lunchthreshold");
        
        desc = (String)theMap.get("description");
        
        lunchstart = (LocalTime)theMap.get("lunchstart");
        lunchstop = (LocalTime)theMap.get("lunchstop");
        
        shiftstart = (LocalTime)theMap.get("shiftstart");
        shiftstop = (LocalTime)theMap.get("shiftstop");
        
    }

    public int getShiftid() {
        return shiftid;
    }

    public String getDesc() {
        return desc;
    }

    public LocalTime getShiftstart() {
        return shiftstart;
    }

    public LocalTime getShiftstop() {
        return shiftstop;
    }

    public int getRoundInterval() {
        return roundInterval;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public int getDockPenalty() {
        return dockPenalty;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getLunchThreshold() {
        return lunchThreshold;
    }

    
    
    public int getShiftDuration() {
        
        if (shiftstart.isBefore(shiftstop)) {
            return (int)(ChronoUnit.MINUTES.between(shiftstart, shiftstop));
        }
        else {
            return 1440 - (int)(ChronoUnit.MINUTES.between(shiftstop, shiftstart));
        }
        
    }
    
    public int getLunchDuration() {
        
        return abs((int)(ChronoUnit.MINUTES.between(lunchstart, lunchstop)));
    
    }
  
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getDesc()).append(": ").append(this.getShiftstart())
        .append(" - ").append(this.getShiftstop()).append(" (").append(this.getShiftDuration())
        .append(" minutes); Lunch: ").append(this.getLunchstart()).append(" - ")
        .append(this.getLunchstop()).append(" (").append(this.getLunchDuration()).append(" minutes)");
        
        String totalStr = s.toString();
        
        return totalStr;
    }
    
}
