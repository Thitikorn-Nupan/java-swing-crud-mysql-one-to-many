package ttknpdev.log;

import org.apache.log4j.Logger;

public class CustomLog4j {
    public Logger log4j;
    public CustomLog4j(Class c) {
        this.log4j = Logger.getLogger(c);
    }
}
