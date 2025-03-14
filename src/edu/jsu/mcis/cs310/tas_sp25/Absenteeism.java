package edu.jsu.mcis.cs310.tas_sp25;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author denzel
 */
public class Absenteeism {
    
    private Employee employee;
    private LocalDate payPeriodStartDate;
    private BigDecimal absenteeismPercentage;
    
    public Absenteeism(Employee employee, LocalDate payPeriodStartDate, BigDecimal absenteeismPercentage) {
    
        /**
         * @param employee The employee object for the absenteeism instance
         * @param payPeriodStartDate The LocalDate object for the payPeriodStartDate for the absenteeism calculation
         * @param absenteeismPercentage The calculated absenteeism percentage for the employee
         */
        this.employee = employee;
        this.payPeriodStartDate = payPeriodStartDate;
        this.absenteeismPercentage = absenteeismPercentage;
    }
    
    /**
     * Getter for the employee class variable
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }
    
    /**
     * Getter for the payPeriodStartDate class variable
     * @return payPeriodStartDate
     */
    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }
    
    /**
     * Getter for the absenteeismPercentage class variable
     * @return absenteeismPercentage
     */
    public BigDecimal getAbsenteeismPercentage() {
        return absenteeismPercentage;
    }
    
    /**
     * toString override for the class
     * @return
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
