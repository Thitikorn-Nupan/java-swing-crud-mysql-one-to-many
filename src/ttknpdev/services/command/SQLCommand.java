package ttknpdev.services.command;

public class SQLCommand {
    public static String EMPLOYEE_READS = "select * from employees_5";
    public static String EMPLOYEE_READ = "select * from employees_5 where eid = ?";
    public static String ADDRESS_READ = "select * from addresses_5 where aid = ?";
    public static String EMPLOYEE_DELETE_WHERE = "delete from employees_5 where eid = ?";
    public static String ADDRESS_DELETE_WHERE = "delete from addresses_5 where aid = ?";
    public static String ADDRESS_BEFORE_DELETE_WHERE = "delete from employees_5_addresses_5 where aid = ?";
    public static String EMPLOYEE_CREATE = "insert into employees_5 values (? , ? , ? , ? , ? , ? ) ";
    public static String EMPLOYEE_UPDATE = "update employees_5 set firstname = ? , lastname = ? , position = ? , active = ? , salary = ? where eid = ? ";
    public static String ADDRESS_UPDATE = "update addresses_5 set country = ? , city = ? , details = ? where aid = ? ";
    public static String ADDRESS_CREATE = "insert into addresses_5 values (? , ? , ? , ? ) ";
    public static String ADDRESS_READS_BY_EID = "select * from employees_5 AS e " +
            "join employees_5_addresses_5 AS e_m " +
            "on e.eid = e_m.eid " +
            "join addresses_5 AS a " +
            "on a.aid = e_m.aid " +
            "where e.eid = ? ";
    public static String EMPLOYEE_ADDRESSES_CREATE = "insert into employees_5_addresses_5 values (? , ? ) ";

}
