package ke.inputs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.properties.Configuration;

public class KEFileInput implements KEInput {

	private static final Logger logger = LoggerFactory.getLogger(KEFileInput.class);

	@Override
	public Collection<Object> getData(Configuration configuration) {
		// TODO Auto-generated method stub

		HashMap<String, String> inputProperties = configuration.getInputProperties();

		Collection<String> inputFolderPaths = inputProperties.values();

		Collection<Object> data = new ArrayList<>();

		for (Iterator<String> inputFolderPathsIterator = inputFolderPaths.iterator(); inputFolderPathsIterator
				.hasNext();) {

			String folderPath = inputFolderPathsIterator.next();

			File inputFolder = new File(folderPath);

			if (inputFolder != null && inputFolder.exists()) {
				if (containsXML(inputFolder)) {
					data.add(inputFolder);
				} else
					logger.error("Folder:" + folderPath + " does not contain XML files.");
			} else
				logger.error("Folder:" + folderPath + " does not exist");

		}

		return data;
	}

	private boolean containsXML(File inputFolder) {

		String[] extensions = { "xml" };

		Collection<File> files = FileUtils.listFiles(inputFolder, extensions, true);

		if (files.isEmpty())
			return false;

		return true;
	}

}
