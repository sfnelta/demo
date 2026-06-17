package de.nelta.demo.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration file", ex);
        }
    }

    public static String getProperty(String key) {
        // Allow system property override
        return System.getProperty(key, properties.getProperty(key));
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
