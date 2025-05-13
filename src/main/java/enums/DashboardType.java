package enums;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Enum {@code Dashboard} class holding the type of dashboards supported by
 * Atomicwork.
 */
public enum DashboardType {
	
	ESD("esd"), ATOM("atom");
	
	private final String dashboardType;
	
	private static List<String> supportedDashboards = new ArrayList<>();
	
	DashboardType(final String dashboardType) {
		this.dashboardType = dashboardType;
	}
	
	/**
	 * Method to fetch the string value of the enum variable.
	 * 
	 * @return String value of the enum variable
	 */
	public String getDashboard() {
		return dashboardType;
	}
	
	/**
	 * Method to list dashboards supported by Atomicwork.
	 * 
	 * @return supported dashboards as a List
	 */
	public static List<String> getSupportedDashboards() {
		if (supportedDashboards.isEmpty()) {
			for (final DashboardType eachType : DashboardType.values())
				supportedDashboards.add(eachType.getDashboard());
		}
		return Collections.unmodifiableList(supportedDashboards);
	}
}