package ke.processing.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ariadne.util.JDomUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.algorithms.KEAlgorithm;
import ke.algorithms.TypicalAlgorithm;
import ke.outputs.KEFileOutput;
import ke.outputs.KEOutput;
import ke.processing.Record;
import ke.properties.Configuration;

public class FileWalkerTest {
	private static final Logger logger = LoggerFactory.getLogger(FileWalkerTest.class);

	@Test
	public void testAppendElement() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Configuration configuration = new Configuration();

		KEAlgorithm algorithm = new TypicalAlgorithm();
		KEOutput output = new KEFileOutput();

		FileWalker fileWalker = new FileWalker(new SAXBuilder(), configuration, algorithm, output);

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/home/kostas/Desktop/TNC/TNC_8715897.xml");
		Document document;

		try {
			document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			Record record = new Record();
			record.setMetadata(rootNode);

			fileWalker.appendElement(configuration.getElement2import(),
					Namespace.getNamespace(configuration.getElementPrefix(), configuration.getElementURI()), record,
					"shit text");

			logger.info(JDomUtils.parseXml2string(record.getMetadata()));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSortMap() {
		Configuration configuration = new Configuration();

		KEAlgorithm algorithm = new TypicalAlgorithm();

		KEOutput output = new KEFileOutput();

		FileWalker fileWalker = new FileWalker(new SAXBuilder(), configuration, algorithm, output);

		Map<String, Integer> map = new HashMap<>();
		map.put("value1", 1);
		map.put("value2", 5);
		map.put("value3", 2);
		map.put("value4", 8);
		map.put("value5", 10);
		map.put("value6", 4);

		List<String> sortMapKeys = fileWalker.getKeysBasedOnFrequencies(map);

		logger.info(sortMapKeys.toString());
	}

	@Test
	public void testSortMapSameValues() {
		Configuration configuration = new Configuration();

		KEAlgorithm algorithm = new TypicalAlgorithm();

		KEOutput output = new KEFileOutput();

		FileWalker fileWalker = new FileWalker(new SAXBuilder(), configuration, algorithm, output);

		Map<String, Integer> map = new HashMap<>();
		map.put("value1", 1);
		map.put("value2", 1);
		map.put("value3", 1);
		map.put("value4", 1);
		map.put("value5", 1);
		map.put("value6", 1);

		List<String> sortMapKeys = fileWalker.getKeysBasedOnFrequencies(map);

		logger.info(sortMapKeys.toString());
	}

}
