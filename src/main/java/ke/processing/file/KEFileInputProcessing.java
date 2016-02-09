package ke.processing.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.algorithms.KEAlgorithm;
import ke.inputs.KEInput;
import ke.outputs.KEOutput;
import ke.processing.ProcessData;
import ke.properties.Configuration;

public class KEFileInputProcessing implements ProcessData {

	private static final Logger logger = LoggerFactory.getLogger(KEFileInputProcessing.class);

	@Override
	public void processData(KEInput input, KEAlgorithm algorithm, KEOutput output, Configuration conf) {
		// TODO Auto-generated method stub

		Collection<Object> dataFolders = input.getData(conf);

		SAXBuilder builder = new SAXBuilder();
		for (Iterator iterator = dataFolders.iterator(); iterator.hasNext();) {
			File folder = (File) iterator.next();

			FileWalker xmlfileWalker = new FileWalker(builder, conf, algorithm, output);

			Set<FileVisitOption> opts = Collections.emptySet();
			try {
				Files.walkFileTree(folder.toPath(), opts, Integer.MAX_VALUE, xmlfileWalker);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

		}

	}

}
