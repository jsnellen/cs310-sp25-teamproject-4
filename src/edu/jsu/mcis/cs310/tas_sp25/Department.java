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

    private final int id;
    private final String description;
    private final int terminal_Id;

    /**
     * Constructs a Department object with the given ID, description, and
     * terminal ID.
     *
     * @param id The unique ID of the department.
     * @param description The department name or description.
     * @param terminalId The terminal ID associated with the department.
     */
    public Department(int id, String description, int terminalId) {
        this.id = id;
        this.description = description;
        this.terminal_Id = terminalId;
    }

    /**
     * Gets the department ID.
     *
     * @return The department ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the department description.
     *
     * @return The department description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the terminal ID associated with the department.
     *
     * @return The terminal ID.
     */
    public int getTerminalId() {
        return terminal_Id;
    }

    /**
     * Returns a string representation of the department.
     *
     * @return A formatted string containing department details.
     */
    @Override
    public String toString() {
        return String.format("#%d (%s), Terminal ID: %d", id, description, terminal_Id);
    }

    /**
     * Checks if two Department objects are equal based on their ID.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Department that = (Department) obj;
        return id == that.id;
    }

    /**
     * Generates a hash code for the Department object based on the ID.
     *
     * @return A hash code for the Department.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
