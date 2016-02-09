package ke.outputs;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import ke.processing.Record;
import ke.properties.Configuration;

public class KEFileOutputTest {

	@Test
	public void testStoreKERecord() {
		KEOutput output = new KEFileOutput();
		Configuration configuration = new Configuration();

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("/home/kostas/Desktop/TNC/TNC_8715897.xml");
		Document document;
		try {
			document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			// rootNode.setNamespace(OaiUtils.LOMLOMNS);
			// rootNode.setNamespace(Namespace.getNamespace("pre",configuration.getElementURI()));

			Record record = new Record();
			record.setMetadata(rootNode);
			output.storeKERecord(record, configuration.getOutputProperties());

			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
