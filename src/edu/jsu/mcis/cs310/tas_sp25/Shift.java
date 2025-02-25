package edu.jsu.mcis.cs310.tas_sp25;

import java.time.DayOfWeek;
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
    
    private String description;
    private final int id;
    private final DailySchedule defaultschedule;
    private final HashMap<DayOfWeek, DailySchedule> weeklyschedule = new HashMap();
    
    public Shift(Map<String, String> shiftInfo) {
    
        this.description = (String)shiftInfo.get("description");
        this.id = Integer.parseInt(shiftInfo.get("id"));
        
        this.defaultschedule = new DailySchedule(shiftInfo);
        
        for(int i = 1; i <= 5; i++) {
        
            weeklyschedule.put(DayOfWeek.of(i), defaultschedule);
        }
    }
    
    public int getId() {
        return id;
    }
    
    
    public String getDescription() {
        return description;
    }

    
    public int getRoundInterval() {
        return defaultschedule.getRoundinterval();
    }

    
    public int getGracePeriod() {
        return defaultschedule.getGraceperiod();
    }

    
    public int getDockPenalty() {
        return defaultschedule.getDockpenalty();
    }

    
    public int getLunchThreshold() {
        return defaultschedule.getLunchthreshold();
    }

    
    public LocalTime getShiftStart() {
        return defaultschedule.getShiftstart();
    }

    
    public LocalTime getShiftStop() {
        return defaultschedule.getShiftstop();
    }

    
    public LocalTime getLunchStart() {
        return defaultschedule.getLunchstart();
    }

    
    public LocalTime getLunchStop() {
        return defaultschedule.getLunchstop();
    }
    
    
    public DailySchedule getDefaultSchedule() {
        return defaultschedule;
    }
    
    
    public DailySchedule getDailySchedule(DayOfWeek dayofweek) {  
        return weeklyschedule.get(dayofweek);
    }

    
    public long getLunchDuration() {
        return defaultschedule.getLunchduration();
    }

    
    public long getShiftDuration() {
        return defaultschedule.getShiftduration();
    }
    
    
    public void setDailySchedule(int dayofweek, Map<String, String> shiftInfo){
        
        weeklyschedule.put(DayOfWeek.of(dayofweek), new DailySchedule(shiftInfo));
        
    }

   
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, defaultschedule.getShiftstart(), defaultschedule.getShiftstop(), defaultschedule.getShiftduration(), defaultschedule.getLunchstart(), defaultschedule.getLunchstop(), defaultschedule.getLunchduration());
    }
    
}
