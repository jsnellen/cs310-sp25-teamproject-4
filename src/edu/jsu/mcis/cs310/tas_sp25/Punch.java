// @author Evan Ranjitkar

package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;

public class Punch{
    
   // Declaring variables to required for the class
   private int id;
   private int terminalId;
   private Badge badge;
   private EventType punchType;
   private LocalDateTime originalTimeStamp;
   private LocalDateTime adjustedTimeStamp;
   
   // First Constructor
   public Punch(int terminalId, Badge badge, EventType punchType){
       this.terminalId = terminalId;
       this.badge = badge;
       this.punchType = punchType;
       this.originalTimeStamp = LocalDateTime.now();
   }
   
   // Second Constructor
   public Punch(int id, int terminalId, Badge badge, LocalDateTime originaltimestamp, EventType punchType){
       this.id = id;
       this.terminalId = terminalId;
       this.badge = badge;
       this.punchType = punchType;
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

    public EventType getPunchType() {
        return punchType;
    }

    public LocalDateTime getOriginalTimeStamp() {
        return originalTimeStamp;
    }

    public LocalDateTime getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }
    
    // Print methods
    
    public void printOriginal(){
       System.out.println("TERMINAL ID: " + this.getId() + "\nBADGE: " + this.getBadge() + "PUNCH TYPE: " + this.getPunchType());
    }
   
    public void printAdjusted(){
       
    }
}