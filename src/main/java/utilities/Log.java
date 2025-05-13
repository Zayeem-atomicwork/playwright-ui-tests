package utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class {@code Log} includes methods related to logging. To use this Logger
 * class, provide "log4j.configurationFile" system property through command
 * line, if not provided, keep the configuration file in
 * "src/main/resources/configs" directory.
 */
public final class Log {
    private static final String LOG4J2_PROPERTIES_FILE_PATH = "src/main/resources/configs/log4j2.properties";
    
    private Log() {
    }
    
    /*
     * static block to set the log properties file location.
     */
    static {
        System.setProperty("log4j.configurationFile", getLog4jConfigFilePath());
    }
    
    /**
     * Method to return Logger object used for logging in different classes. Note:
     * Log4j2 keeps a hash table of already created Loggers so it will not create a
     * new Logger in case one already exists for the same caller class.
     * 
     * @param clazz: class
     * @return object of Logger class
     */
    public static Logger getLogger(final Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
    
    /**
     * Method to return the log4j configuration file path. If
     * "log4j.configurationFile" system property is present, then it will be used
     * else LOG4J2_PROPERTIES_FILE_PATH is used.
     * 
     * @return log4j configuration file path as a {@link String}.
     */
    private static String getLog4jConfigFilePath() {
        final String configFilePathFrmCmd = System.getProperty("log4j.configurationFile");
        return StringUtils.isBlank(configFilePathFrmCmd) ? LOG4J2_PROPERTIES_FILE_PATH : configFilePathFrmCmd;
    }
}