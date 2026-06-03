package ttknpdev.services;

import ttknpdev.configuration.DbConfig;
import ttknpdev.entities.Address;
import ttknpdev.entities.Employee;
import ttknpdev.log.CustomLog4j;
import ttknpdev.repositories.AddressRepository;
import ttknpdev.services.command.SQLCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressService implements AddressRepository<Address> {
    private DbConfig dbConfig;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CustomLog4j customLog4j;

    public AddressService() {
        dbConfig = new DbConfig();
        connection = dbConfig.getConnect();
        customLog4j = new CustomLog4j(AddressService.class);
    }

    @Override
    public List<Address> reads() {
        return null;
    }

    @Override
    public Address read(String aid) {
        ResultSet resultSet;
        Address address = null;
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_READ);
            preparedStatement.setString(1, aid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                address = new Address(resultSet.getString("aid"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("details")
                );
            }
            return address;
        } catch (SQLException | NullPointerException exception) {
            customLog4j.log4j.warn("SQLException class has error : " + exception.getMessage());
            return null; // return for condition in next step
        }
    }

    @Override
    public Employee readsByEid(String eid) {
        ResultSet resultSet;
        Employee employee = null;
        List<Address> addresses = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_READS_BY_EID);
            preparedStatement.setString(1, eid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee = new Employee(resultSet.getString("eid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("position"),
                        resultSet.getBoolean("active"),
                        resultSet.getFloat("salary"));
                addresses.add(new Address(
                        resultSet.getString("aid"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("details")
                ));
            }

            employee.setAddresses(addresses); // add List addresses to List addresses of Employee
            customLog4j.log4j.info(employee);
            customLog4j.log4j.info(employee.getAddresses());
            return employee;

        } catch (SQLException | NullPointerException exception) {
            customLog4j.log4j.warn("SQLException class has error : " + exception.getMessage());
            return null; // return for condition in next step
        }
    }

    @Override
    public Integer create(Address obj) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_CREATE);
            preparedStatement.setString(1, obj.getAid());
            preparedStatement.setString(2, obj.getCountry());
            preparedStatement.setString(3, obj.getCity());
            preparedStatement.setString(4, obj.getDetails());
            customLog4j.log4j.info("created");
            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer createRelations(String eid, String aid) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.EMPLOYEE_ADDRESSES_CREATE);
            preparedStatement.setString(1, eid);
            preparedStatement.setString(2, aid);
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer update(Address obj) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_UPDATE);
            preparedStatement.setString(1, obj.getCountry());
            preparedStatement.setString(2, obj.getCity());
            preparedStatement.setString(3, obj.getDetails());
            preparedStatement.setString(4, obj.getAid());
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            customLog4j.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer delete(String aid) {
        try {
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_BEFORE_DELETE_WHERE);
            preparedStatement.setString(1, aid);
            customLog4j.log4j.info("deleted relation");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(SQLCommand.ADDRESS_DELETE_WHERE);
            preparedStatement.setString(1, aid);
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
