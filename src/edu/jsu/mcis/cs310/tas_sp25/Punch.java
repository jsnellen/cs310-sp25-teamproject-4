/**
 *
 * @author evanranjitkar
 */

package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch{
    
   // Declaring variables to required for the class
   private int id;
   private int terminalId;
   private Badge badge;
   private EventType eventType;
   private LocalDateTime originalTimeStamp;
   private LocalDateTime adjustedTimeStamp;
   
   // First Constructor
   public Punch(int terminalId, Badge badge, EventType eventType){
       this.terminalId = terminalId;
       this.badge = badge;
       this.eventType = eventType;
       this.originalTimeStamp = LocalDateTime.now();
   }
   
   // Second Constructor
   public Punch(int id, int terminalId, Badge badge, LocalDateTime originaltimestamp, EventType eventType){
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
    
    // Print methods
    
    public String printOriginal(){
        
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
   
    public void printAdjusted(){
       
    }
}