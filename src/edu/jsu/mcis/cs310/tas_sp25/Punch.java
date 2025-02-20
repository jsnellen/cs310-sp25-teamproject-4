// @author Evan Ranjitkar

package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;

public class Punch{
    
   // Declaring variables to required for the class
   private int id;
   private int terminalid;
   private Badge badge;
   private EventType punchType;
   private LocalDateTime originalTimeStamp;
   private LocalDateTime adjustedTimeStamp;
   
   // First Constructor
   public Punch(int terminalid, Badge badge, EventType punchtype){
       this.terminalid = terminalid;
       this.badge = badge;
       this.punchType = punchtype;
       this.originalTimeStamp = LocalDateTime.now();
   }
   
   // Second Constructor
   public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
       this.id = id;
       this.terminalid = terminalid;
       this.badge = badge;
       this.punchType = punchtype;
       this.originalTimeStamp = originaltimestamp;
   }

   // Getter Methods
    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
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