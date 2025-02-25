/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 *
 * @author denzel
 */
class DailySchedule {
    
    private final LocalTime shiftstart;
    private final LocalTime shiftstop;
    private final LocalTime lunchstart;
    private final LocalTime lunchstop;
    private final int roundinterval;
    private final int graceperiod;
    private final int dockpenalty;
    private final int lunchthreshold;
    private final long lunchduration;
    private final long shiftduration;
    
    public DailySchedule(Map<String, String> scheduleInfo) {
    
        this.shiftstart = LocalTime.parse(scheduleInfo.get("shiftstart"));
        this.shiftstop = LocalTime.parse(scheduleInfo.get("shiftstop"));
        this.lunchstart = LocalTime.parse(scheduleInfo.get("lunchstart"));
        this.lunchstop = LocalTime.parse(scheduleInfo.get("lunchstop"));
        this.roundinterval = Integer.parseInt(scheduleInfo.get("roundinterval"));
        this.graceperiod = Integer.parseInt(scheduleInfo.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(scheduleInfo.get("dockpenalty"));
        this.lunchthreshold = Integer.parseInt(scheduleInfo.get("lunchthreshold"));
        
        this.lunchduration = ChronoUnit.MINUTES.between(lunchstart, lunchstop);
        this.shiftduration = ChronoUnit.MINUTES.between(shiftstart, shiftstop);
    }
    
    public LocalTime getShiftstart() {
        return shiftstart;
    }

    
    public LocalTime getShiftstop() {
        return shiftstop;
    }

    
    public LocalTime getLunchstart() {
        return lunchstart;
    }

    
    public LocalTime getLunchstop() {
        return lunchstop;
    }

   
    public int getRoundinterval() {
        return roundinterval;
    }

    
    public int getGraceperiod() {
        return graceperiod;
    }

    
    public int getDockpenalty() {
        return dockpenalty;
    }

    
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
    
    public long getLunchduration() {
        return lunchduration;
    }
    
    
    public long getShiftduration() {
        return shiftduration;
    }
    
}
