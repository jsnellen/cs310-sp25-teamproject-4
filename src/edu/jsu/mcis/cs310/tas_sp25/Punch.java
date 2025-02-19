package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;

public class Punch{
    
   private int terminalid;
   private Badge badge;
   private EventType punchType;
   
   private int id;
   private LocalDateTime orginalTimeStamp;
  
   private LocalDateTime adjustedTimeStamp;
   
   public Punch(int terminalid, Badge badge, EventType punchtype){
       this.terminalid = terminalid;
       this.badge = badge;
       this.punchType = punchtype;
   }
   
   public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
       this.id = id;
       this.terminalid = terminalid;
       this.badge = badge;
       this.punchType = punchtype;
   }
   
   public void printOriginal(){
       
   }
   
   public void printAdjusted(){
       
   }
}