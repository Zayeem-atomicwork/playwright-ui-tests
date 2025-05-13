package dataproviders;

import java.awt.Toolkit;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.microsoft.playwright.options.ViewportSize;

//import com.atomicwork.utilities.ConvertUtils;
//import com.atomicwork.utilities.file.YamlFileReader;

import enums.BrowserType;
import enums.DashboardType;
import enums.EnvironmentType;
import utilities.ConvertUtils;
import utilities.YamlFileReader;

/**
 * The class {@code ConfigFileReader} is used to read the {@code .yaml}
 * configuration file. This yaml file helps us in avoiding any hard coded
 * values.
 */
@SuppressWarnings("unchecked")
public class ConfigFileReader {

	private final Map<String, Object> config;
	private static final String CONFIG_FILE_PATH = "src/test/resources/configs/config.yaml";
	private Map<String, Object> envConfig, browserConfig;
	private static final int IMPLICIT_TIME_OUT = 5;
	private BrowserType browserType;
	private static final int EXPLICIT_TIME_OUT = 10;

	public ConfigFileReader() {
		final YamlFileReader yamlFileReader = new YamlFileReader(CONFIG_FILE_PATH);
		config = yamlFileReader.getMapFromYaml();
	}

	/**
	 * Method to fetch the type of dashboard.
	 * 
	 * @return dashboard type as an ENUM.
	 */
	private DashboardType getDashboardType() {
		final String dashboardFrmCmd = System.getProperty("dashboardType");
		switch (dashboardFrmCmd) {
		case "esd":
			return DashboardType.ESD;
		default:
			throw new IllegalArgumentException(String.format("Unsupported dashboard: %s. Supported are: %s",
					dashboardFrmCmd, DashboardType.getSupportedDashboards()));
		}
	}

	/**
	 * Method to fetch the type of environment.
	 * 
	 * @return environment type as an ENUM.
	 */
	private EnvironmentType getEnvType() {
		final String envNameFrmCmd = System.getProperty("envType");
		switch (envNameFrmCmd) {
		case "m2":
			return EnvironmentType.M2;
		case "prod":
			return EnvironmentType.PROD;
		case "stage":
			return EnvironmentType.STAGE;
		case "prelive":
			return EnvironmentType.PRELIVE;
		default:
			throw new IllegalArgumentException(String.format("Unsupported environment: %s. Supported are: %s",
					envNameFrmCmd, EnvironmentType.getSupportedEnvs()));
		}
	}

	/**
	 * Method to fetch environment configuration as a Map.
	 * 
	 * @return environment configuration as a Map.
	 */

	private Map<String, Object> getEnvConfig() {
		if (envConfig != null)
			return envConfig;
		final DashboardType dashboardType = getDashboardType();
		final EnvironmentType envType = getEnvType();
		String dashboardTypeKey = null, envTypeKey = null;
		if (dashboardType == DashboardType.ESD && envType == EnvironmentType.M2) {
			dashboardTypeKey = "esd";
			envTypeKey = "e2e";
		} else if (dashboardType == DashboardType.ESD && envType == EnvironmentType.PROD) {
			dashboardTypeKey = "esd";
			envTypeKey = "prod";
		} else if (dashboardType == DashboardType.ESD && envType == EnvironmentType.STAGE) {
			dashboardTypeKey = "esd";
			envTypeKey = "stage";
		} else if (dashboardType == DashboardType.ESD && envType == EnvironmentType.PRELIVE) {
			dashboardTypeKey = "esd";
			envTypeKey = "dev";
		} else if (dashboardType == DashboardType.ATOM && envType == EnvironmentType.M2)
			throw new RuntimeException(String.format("Unsupported dashboard: %s for M2 environment.", dashboardType));
		else if (dashboardType == DashboardType.ATOM && envType == EnvironmentType.PROD) {
			dashboardTypeKey = "atom";
			envTypeKey = "prod";
		}
		envConfig = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) config.get("dashboard"))
				.get(dashboardTypeKey)).get(envTypeKey);
		return envConfig;
	}

	/**
	 * Method to fetch browser configuration as a Map.
	 * 
	 * @return browser configuration as a Map.
	 */

	private Map<String, Object> getBrowserConfig() {
		return (browserConfig == null) ? browserConfig = (Map<String, Object>) config.get("browser") : browserConfig;
	}

	/**
	 * Method to fetch login page URL from the config.yaml file.
	 * 
	 * @return login page URL as a String.
	 */
	public String getLoginPageUrl() {
		return getEnvConfig().get("loginPageUrl").toString();
	}

	/**
	 * Method to fetch the type of browser on which tests will run. Browser based on
	 * command line parameter if present else will be returned from config.yaml
	 * file.
	 * 
	 * @return driver type as an ENUM.
	 */
	public BrowserType getBrowserType() {
		if (browserType != null)
			return browserType;
		final String browserFrmCmd = System.getProperty("browser");
		final String browserName = StringUtils.isBlank(browserFrmCmd) ? getBrowserConfig().get("type").toString()
				: browserFrmCmd;
		switch (browserName) {
		case "chrome":
			browserType = BrowserType.CHROME;
			break;
		case "webkit":
			browserType = BrowserType.WEBKIT;
			break;
		case "firefox":
			browserType = BrowserType.FIREFOX;
			break;
		default:
			throw new IllegalArgumentException(String.format("Unsupported browser: %s", browserName));
		}
		return browserType;
	}

	/**
	 * If the browser window needs to be maximized. false only if set explicitly in
	 * the config.yaml file.
	 * 
	 * @return if window should be maximized as a Boolean.
	 */
	public Boolean maximizeWindow() {
		final String windowMaximize = getBrowserConfig().get("windowMaximize").toString();
		if (windowMaximize != null)
			return Boolean.valueOf(windowMaximize);
		return true;
	}

	/**
	 * If the browser cookies needs to be deleted. false only if set explicitly in
	 * the config.yaml file.
	 * 
	 * @return if cookies should be deleted as a Boolean.
	 */
	public Boolean deleteCookies() {
		final String deleteCookies = getBrowserConfig().get("deleteCookies").toString();
		if (deleteCookies != null)
			return Boolean.valueOf(deleteCookies);
		return true;
	}

	/**
	 * Method to get implicit wait to be set for the driver session.
	 * 
	 * @return implicit wait from the config.yaml file if present else defined by
	 *         IMPLICIT_TIME_OUT variable is returned.
	 */
	public int getImplicitlyWait() {
		final String implicitWaitTime = getBrowserConfig().get("implicitWaitTime").toString();
		if (implicitWaitTime != null)
			return ConvertUtils.stringToInt(implicitWaitTime);
		return IMPLICIT_TIME_OUT;
	}

	/**
	 * Method to get browser dimension to be set for the browser on which tests will
	 * run. If not present in config.yaml file, current dimensions of screen are
	 * returned.
	 * 
	 * @return dimension to be set for browser as a ViewportSize class object.
	 */
	public ViewportSize getBrowserDimension() {
		final String[] browserDimension = getBrowserConfig().get("dimension").toString().replace(" ", "").split(",");
		if (browserDimension.length == 2) {
			try {
				return new ViewportSize(Integer.parseInt(browserDimension[0]), Integer.parseInt(browserDimension[1]));
			} catch (NumberFormatException e) {
				throw new RuntimeException(e);
			}
		}
		final java.awt.Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		return new ViewportSize(screenDimension.width, screenDimension.height);
	}

	/**
	 * Method to fetch user email from the command line parameters or the
	 * config.yaml file. Command line email parameter is returned if present.
	 * 
	 * @return user email as a String.
	 */
	public String getUserEmail() {
		final String userEmailFrmCmd = System.getProperty("email");
		return StringUtils.isBlank(userEmailFrmCmd) ? getEnvConfig().get("email").toString() : userEmailFrmCmd;
	}

	/**
	 * Method to fetch user password from the command line parameters or the
	 * config.yaml file. Command line password parameter is returned if present.
	 * 
	 * @return user password as a String.
	 */
	public String getUserPassword() {
		final String passwordFromCmd = System.getProperty("password");
		return StringUtils.isBlank(passwordFromCmd) ? getEnvConfig().get("password").toString() : passwordFromCmd;
	}

	/**
	 * Method to get explicit wait to be set for the different web elements as
	 * required.
	 * 
	 * @return explicit wait from the config.yaml file if present else defined by
	 *         EXPLICIT_TIME_OUT variable is returned.
	 */
	public int getExplicitWait() {
		final String explicitWaitTime = getBrowserConfig().get("explicitWaitTime").toString();
		if (explicitWaitTime != null)
			return ConvertUtils.stringToInt(explicitWaitTime);
		return EXPLICIT_TIME_OUT;
	}
	
	/**
	 * If the browser should run in headless mode. Command line parameter takes 
	 * precedence over config file.
	 *
	 * @return if browser should run in headless mode as a Boolean.
	 */
	public Boolean isHeadless() {
	    // First check for command line override
	    String headlessParam = System.getProperty("headless");
	    if (headlessParam != null) {
	        return Boolean.valueOf(headlessParam);
	    }
	    
	    // If no command line parameter, use config file
	    final String headless = getBrowserConfig().get("headless").toString();
	    if (headless != null) {
	        return Boolean.valueOf(headless);
	    }
	    
	    // Default to true if not specified anywhere
	    return true;
	}
}
