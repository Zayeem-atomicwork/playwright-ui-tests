package managers;

import dataproviders.ConfigFileReader;

/**
 * The class {@code FileReaderManager} is a singleton class to manage all file readers
 * in the framework.
 */
public class FileReaderManager {
    
    private static FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigFileReader configFileReader;
    
    private FileReaderManager() {}
    
    /**
     * Method to get instance of the FileReaderManager.
     * 
     * @return instance of FileReaderManager
     */
    public static FileReaderManager getInstance() {
        return fileReaderManager;
    }
    
    /**
     * Method to get instance of the ConfigFileReader.
     * 
     * @return instance of ConfigFileReader
     */
    public ConfigFileReader getConfigReader() {
        return (configFileReader == null) ? configFileReader = new ConfigFileReader() : configFileReader;
    }
}