package edu.jsu.mcis.cs310.tas_sp25.dao;

import java.sql.*;

public final class DAOFactory {

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private final String url, username, password;
    
    private Connection conn = null;

    public DAOFactory(String prefix) {

        DAOProperties properties = new DAOProperties(prefix);

        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }

    Connection getConnection() {
        return conn;
    }

    public BadgeDAO getBadgeDAO() {
        return new BadgeDAO(this);
    }

    public PunchDAO getPunchDAO() {
        return new PunchDAO(this);
    }
    
    

    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAO(this);
    }
    
    public DepartmentDAO getDepartmentDAO(){
        return new DepartmentDAO(this);
    }
    

    public ShiftDAO getShiftDAO(){
        return new ShiftDAO(this);
    }
    
    public AbsenteeismDAO getAbsenteeismDAO(){
        return new AbsenteeismDAO(this);
    }
    
    public ReportDAO getReportDAO(){
        return new ReportDAO(this);
    }
}
