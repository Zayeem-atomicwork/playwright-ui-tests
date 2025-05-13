package utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * The class {@code ScenarioContext} holding data. It uses {@code TestContext}
 * class to share data b/w different steps in a test scenario. It uses a
 * {@code Map} object to storage any number of fields as a key-value pair.
 */
public class ScenarioContext<T> {
    private final Map<String, Object> scenarioContext;

    public ScenarioContext() {
        scenarioContext = new HashMap<>();
    }

    /**
     * Method to set data as a key-value pair inside the {@code Map} object.
     * 
     * @param key:   Enum {@code ContextData} key
     * @param value: value as a object of {@code Object} class to store any type of
     *               data as required.
     */
    public void setContext(final T key, final Object value) {
        scenarioContext.put(key.toString(), value);
    }

    /**
     * Method to fetch data.
     * 
     * @param key: Enum {@code ContextData} key
     * @return data as object of {@code Object} class
     */
    public Object getContext(final T key) {
        return scenarioContext.get(key.toString());
    }
    
    /**
     * Method to fetch typed data.
     * 
     * @param <R> The type to cast the result to
     * @param key: Enum {@code ContextData} key
     * @param clazz: Class of the type to cast to
     * @return data cast to the specified type
     */
    public <R> R getContext(final T key, Class<R> clazz) {
        return clazz.cast(getContext(key));
    }
    
    /**
     * Method to check if context contains a key.
     * 
     * @param key: Enum {@code ContextData} key
     * @return true if key exists, false otherwise
     */
    public boolean containsKey(final T key) {
        return scenarioContext.containsKey(key.toString());
    }
    
    /**
     * Method to clear all context data.
     */
    public void clearContext() {
        scenarioContext.clear();
    }
}