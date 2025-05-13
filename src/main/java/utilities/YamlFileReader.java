package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * The class {@code YamlFileReader} is used to read a YAML file.
 */
public class YamlFileReader {
    private final String yamlFilePath;

    public YamlFileReader(final String yamlFilePath) {
        this.yamlFilePath = yamlFilePath;
    }

    /**
     * Method to fetch a map from a YAML file.
     * 
     * @return {@link Map} object with key of type {@link String} and value of type
     *         {@link Object}
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMapFromYaml() {
        final File yamlFile = new File(yamlFilePath);
        final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Map<String, Object> mapFromYaml;
        try {
            mapFromYaml = objectMapper.readValue(yamlFile, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapFromYaml;
    }
}