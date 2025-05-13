package enums;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enum {@code Environment} class holding the type of environments supported by
 * Atomicwork.
 */
public enum EnvironmentType {

	M2("m2"), PROD("prod"), STAGE("stage"), PRELIVE("prelive");
	private final String envType;
	private static List<String> supportedEnvs = new ArrayList<>();
	
	EnvironmentType(final String envType) {
		this.envType = envType;
	}
	
	/**
	 * Method to fetch the string value of the enum variable.
	 * 
	 * @return String value of the enum variable
	 */
	public String getEnv() {
		return envType;
	}
	
	/**
	 * Method to list environments supported by Atomicwork.
	 * 
	 * @return supported environments as a List
	 */
	public static List<String> getSupportedEnvs() {
		if (supportedEnvs.isEmpty()) {
			for (final EnvironmentType eachType : EnvironmentType.values())
				supportedEnvs.add(eachType.getEnv());
		}
		return Collections.unmodifiableList(supportedEnvs);
	}
}