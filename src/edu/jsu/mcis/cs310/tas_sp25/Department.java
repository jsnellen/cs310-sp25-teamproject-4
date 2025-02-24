/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp25;

/**
 *
 * @author mahinpatel
 */
public class Department {
    private final String description;
    private final int id, terminalid;

    public Department(int id, String description, int terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public int getTerminalId() {
        return terminalid;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append('#').append(id).append(' ');
        s.append('(').append(description).append("), Terminal ID: ");
        s.append(terminalid);

        return s.toString();
        
    }
}
