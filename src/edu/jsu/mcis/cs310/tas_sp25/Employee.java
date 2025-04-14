package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * 
 * The `Employee` class represents an employee with its associated properties such as id, name,
 * badge, department, shift, etc
 * 
 * <p>This class serves as a model for storing employee-related data retrieved from 
 * the database. It supports string representation and access to all key attributes.</p>
 *
 * @author Tanner Thomas
 */
public class Employee {
    // Variables
    private final int id;
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final LocalDateTime active;
    private final Badge badge;
    private final Department department;
    private final Shift shift;
    private final EmployeeType employeeType;
    
    /*
     * Constructs an Employee object with the given parameters
     *
     * @param id The unique ID of the employee
     * @param firstname The first name of the employee
     * @param middlename The middle name of the employee
     * @param lastname The last name of the employee
     * @param active The active status of the employee
     * @param badge The Badge object of the employee instance
     * @param department The Department object of the employee instance
     * @param shift The Shift object of the employee instance
     * @param employeeType The Employee object of the employee instance
     */
    public Employee(int id, String firstname, String middlename, String lastname,
             LocalDateTime active, Badge badge, Department department, Shift shift,
             EmployeeType employeeType) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.employeeType = employeeType;
    }
    
    // Getters
    
    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public LocalDateTime getActive() {
        return active;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public Badge getBadge() {
        return badge;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public Shift getShift() {
        return shift;
    }

    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    
    /**
     * Returns the shift ID.
     *
     * @return The shift ID.
     */
@Override
public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return "ID #" + id + ": " + lastname + ", " + firstname + " " + middlename +
           " (#" + badge.getId() + "), Type: " + employeeType +
           ", Department: " + department.getDescription() + ", Active: " + active.format(formatter);
}
 
}
