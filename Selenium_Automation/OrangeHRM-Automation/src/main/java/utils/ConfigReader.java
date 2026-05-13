package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties prop;
	
	static {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}
	
	
}
