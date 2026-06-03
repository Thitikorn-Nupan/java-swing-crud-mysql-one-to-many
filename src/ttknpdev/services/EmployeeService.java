package ttknpdev.services;

import ttknpdev.configuration.DbConfig;
import ttknpdev.entities.Employee;
import ttknpdev.log.CustomLog4j;
import ttknpdev.repositories.EmployeeRepository;
import ttknpdev.services.command.SQLCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements EmployeeRepository<Employee> {

    private DbConfig dbConfig;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CustomLog4j customLog4j;

    public EmployeeService() {
        dbConfig = new DbConfig(); // initial connect database
        connection = dbConfig.getConnect();
        customLog4j = new CustomLog4j(EmployeeService.class);
    }

    @Override
    public List<Employee> reads() {
        ResultSet resultSet;
        List<Employee> employeeList;
        try {
            employeeList = new ArrayList<>();
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_READS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeList.add(new Employee
                        (
                                resultSet.getString("eid"),
                                resultSet.getString("firstname"),
                                resultSet.getString("lastname"),
                                resultSet.getString("position"),
                                resultSet.getBoolean("active"),
                                resultSet.getFloat("salary")
                        )
                );
            }
            return employeeList;
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
        }

    }

    @Override
    public Employee read(String eid) {
        ResultSet resultSet;
        Employee employee = null;
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_READ);
            preparedStatement.setString(1, eid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getString("eid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("position"),
                        resultSet.getBoolean("active"),
                        resultSet.getFloat("salary"));
            }
            customLog4j.log4j.info(employee);
            return employee;
        } catch (SQLException sqle) {
            customLog4j.log4j.warn("SQLException class has error : " + sqle.getMessage());
            return null;
        }
    }

    @Override
    public Integer delete(String eid) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_DELETE_WHERE);
            preparedStatement.setString(1, eid);
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer create(Employee obj) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_CREATE);
            preparedStatement.setString(1, obj.getEid());
            preparedStatement.setString(2, obj.getFirstname());
            preparedStatement.setString(3, obj.getLastname());
            preparedStatement.setString(4, obj.getPosition());
            preparedStatement.setBoolean(5, obj.getActive());
            preparedStatement.setFloat(6, obj.getSalary());
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer update(Employee obj) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_UPDATE);
            preparedStatement.setString(1, obj.getFirstname());
            preparedStatement.setString(2, obj.getLastname());
            preparedStatement.setString(3, obj.getPosition());
            preparedStatement.setBoolean(4, obj.getActive());
            preparedStatement.setFloat(5, obj.getSalary());
            preparedStatement.setString(6, obj.getEid());
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public void closeConnect() {
        dbConfig.closeConnect();
    }
}
