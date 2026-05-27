package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties prop;
	
	static {
		try {
			prop = new Properties();
			InputStream input = ConfigReader.class.getClassLoader()
				.getResourceAsStream("config/config.properties");
			
			if (input == null) {
				throw new Exception("config.properties not found in classpath!");
			}
			prop.load(input);
			input.close();
		} catch (Exception e) {
			System.err.println("CRITICAL: Failed to load config.properties");
			e.printStackTrace();
			throw new RuntimeException("ConfigReader initialization failed", e);
		}
	}

	public static String get(String key) {
		String value = prop.getProperty(key);
		if (value == null) {
			System.err.println("WARNING: Property '" + key + "' not found in config.properties");
		}
		return value;
	}
	
	
}
