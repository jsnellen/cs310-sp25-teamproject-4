package edu.jsu.mcis.cs310.tas_sp25;

import java.time.LocalDateTime;
/**
 *
 * @author Tanner
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
    
    // Constructors
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
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDateTime getActive() {
        return active;
    }

    public Badge getBadge() {
        return badge;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShift() {
        return shift;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    
    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firstname=" + firstname + ", middlename=" + middlename + ", lastname=" + lastname + ", active=" + active + ", badge=" + badge + ", department=" + department + ", shift=" + shift + ", employeeType=" + employeeType + '}';
    }
    
    
    
}
