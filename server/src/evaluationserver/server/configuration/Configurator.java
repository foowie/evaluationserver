package evaluationserver.server.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configurator {
	
	protected Map<String, String> config = new HashMap<String, String>();
	
	public void loadConfig(File file) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		for(String key : properties.stringPropertyNames())
			config.put(key, properties.getProperty(key));
	}
	
	public String getVariable(String key) {
		return getVariable(key, null);
	}
	
	public String getVariable(String key, String def) {
		return config.containsKey(key) ? config.get(key) : def;
	} 
	
}
