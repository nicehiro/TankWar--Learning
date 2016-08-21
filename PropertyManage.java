import java.io.IOException;
import java.util.Properties;

public class PropertyManage {
	static Properties prop = new Properties();
	
	static {
		try {
			prop.load(PropertyManage.class.getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}
