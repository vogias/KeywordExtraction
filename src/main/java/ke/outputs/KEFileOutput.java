package ke.outputs;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.ariadne.util.JDomUtils;
import org.ariadne.util.OaiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.processing.Record;

public class KEFileOutput implements KEOutput {
	private static final Logger logger = LoggerFactory.getLogger(KEFileOutput.class);

	@Override
	public void storeKERecord(Record metadata, Map<String, String> parameters) {
		// TODO Auto-generated method stub

		String xmlString = JDomUtils.parseXml2string(metadata.getMetadata().getDocument(), null);

		Collection<String> values = parameters.values();

		for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
			String targetFilePathString = iterator.next();

			try {
				logger.info("Storing record to file:" + targetFilePathString + File.separatorChar
						+ metadata.getRecordName());
				OaiUtils.writeStringToFileInEncodingUTF8(xmlString,
						targetFilePathString + File.separatorChar + metadata.getRecordName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

		}

	}

}
