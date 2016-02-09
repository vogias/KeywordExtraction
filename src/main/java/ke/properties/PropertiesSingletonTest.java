package ke.properties;

import org.junit.Test;

public class PropertiesSingletonTest {

	@Test
	public void testGetInstance() {
		PropertiesSingleton.getInstance();

	}

	@Test
	public void testConfiguration() {
		Configuration configuration = new Configuration();
		System.out.println(configuration.getAlgorithmClass());

		System.out.println(configuration.getInputProperties());

	}

	@Test
	public void testConfigurationIntegrity() {
		Configuration configuration = new Configuration();

		configuration.checkPropertiesIntegrity();

	}

}
