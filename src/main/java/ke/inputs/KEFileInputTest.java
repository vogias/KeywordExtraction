package ke.inputs;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.properties.Configuration;

public class KEFileInputTest {
	private static final Logger logger = LoggerFactory.getLogger(KEFileInputTest.class);

	@Test
	public void testGetData() {
		Configuration configuration = new Configuration();
		KEInput input = new KEFileInput();
		logger.info(input.getData(configuration).toString());

	}

}
