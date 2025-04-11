package edu.jsu.mcis.cs310.tas_sp25;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an absenteeism record for an employee within a specific pay period.
 * This class holds the employee information, the start date of the pay period,
 * and the absenteeism percentage for that period. It is used to track employee 
 * absenteeism data and provides a formatted string representation for reporting.
 * 
 * @author Denzel Stinson
 */
public class Absenteeism {
    
    private Employee employee;
    private LocalDate payPeriodStartDate;
    private BigDecimal absenteeismPercentage;
    
    /**
         * Constructs an Absenteeism object with given employee, pay period start date,
         * and absenteeism percentage.
         * 
         * @param employee The employee for whom absenteeism is being recorded.
         * @param payPeriodStartDate The start date of the pay period.
         * @param absenteeismPercentage The absenteeism percentage for the employee during this pay period.
         * 
         */
    public Absenteeism(Employee employee, LocalDate payPeriodStartDate, BigDecimal absenteeismPercentage) {
    
        this.employee = employee;
        this.payPeriodStartDate = payPeriodStartDate;
        this.absenteeismPercentage = absenteeismPercentage;
    }
    
    /**
     * Returns the employee associated with this absenteeism record.
     * 
     * @return The employee object associated with this absenteeism record.
     */
    public Employee getEmployee() {
        return employee;
    }
    
    /**
     * Returns the start date of the pay period for this absenteeism record.
     * 
     * @return The pay period start date as a LocalDate object.
     */
    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }
    
    /**
     * Returns the absenteeism percentage for the employee during the specified pay period.
     * 
     * @return The absenteeism percentage as a BigDecimal.
     */
    public BigDecimal getAbsenteeismPercentage() {
        return absenteeismPercentage;
    }
    
    /**
     * Returns a string representation of this absenteeism record in the format:
     * <br>#EmployeeID (Pay Period Starting MM-dd-yyyy): X.XX%
     * 
     * <p>The format is as follows:</p>
     * <ul>
     *      <li><b>EmployeeID</b> is the ID of the employee.</li>
     *      <li><b>Pay Period Starting</b> is the formatted start date of the pay period.</li>
     *      <li><b>X.XX%</b> is the absenteeism percentage, rounded to two decimal places.</li>
     * </ul>
     * 
     * @return A formatted string representing the absenteeism record.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = payPeriodStartDate.format(formatter); // Reformat the date
        return String.format("#%s (Pay Period Starting %s): %.2f%%",
                employee.getBadge().getId(),
                formattedDate,
                absenteeismPercentage);
    }
    
    
}
