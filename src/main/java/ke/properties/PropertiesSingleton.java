package ke.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesSingleton {

	private static PropertiesSingleton instance = null;
	private static Properties props = null;

	private PropertiesSingleton() {

		props = new Properties();
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream("configure.properties");
			props.load(fileInputStream);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

	}

	protected static synchronized Properties getInstance() {
		if (instance == null) {
			instance = new PropertiesSingleton();
		}
		return props;
	}

}
