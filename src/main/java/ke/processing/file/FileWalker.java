/**
 * 
 */
package ke.processing.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.ariadne.util.JDomUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.algorithms.KEAlgorithm;
import ke.entry.ClassObjectCreation;
import ke.outputs.KEOutput;
import ke.processing.Record;
import ke.properties.Configuration;

/**
 * @author vogias
 * 
 */
public class FileWalker extends SimpleFileVisitor<Path> {
	private static final Logger logger = LoggerFactory.getLogger(FileWalker.class);

	SAXBuilder builder;
	String elementPath, nameSpace, element2import, prefix;
	KEAlgorithm algorithm;
	int number_of_keywords2insert = 0;
	KEOutput output;
	Map<String, String> outputProperties;
	String analyzerClass = "org.apache.lucene.analysis.en.EnglishAnalyzer";

	public FileWalker(SAXBuilder builder, Configuration conf, KEAlgorithm algorithm, KEOutput output) {
		// TODO Auto-generated constructor stub

		this.builder = builder;
		this.elementPath = conf.getElement2analyze();
		this.nameSpace = conf.getElementURI();
		this.element2import = conf.getElement2import();
		this.algorithm = algorithm;
		this.number_of_keywords2insert = conf.getKeywords2insert();
		this.prefix = conf.getElementPrefix();
		this.output = output;
		this.analyzerClass = conf.getAnalyzerClass();

		this.outputProperties = conf.getOutputProperties();
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

	private String normalizeName(String name) {
		String normalizedName = name.substring(name.lastIndexOf(":") + 1, name.length());

		return normalizedName;
	}

	protected Record appendElement(String elementPath, Namespace namespace, Record record, String elementText) {

		String[] nodes = elementPath.split("/");

		String nodePath = "/";
		Element previous = null;
		for (int i = 0; i < nodes.length; i++) {
			String currentNodeName = nodes[i];
			if (currentNodeName.trim().equals(""))
				continue;

			try {

				nodePath += "/" + currentNodeName;
				logger.info("Checking node:" + nodePath + " if exists");
				Element node = JDomUtils.getXpathNode(nodePath, namespace, record.getMetadata());
				if (node == null && previous == null) {
					logger.error("Wrong ke.element.output value");
					System.exit(-1);

				}
				if (node != null) {
					logger.info("it does");
					if (i == nodes.length - 1) {
						Element node2add = new Element(normalizeName(currentNodeName), namespace);
						node2add.setText(elementText);
						previous.addContent(0, node2add);
						break;
					}
					previous = node;
					continue;
				}
				if (node == null && previous != null) {
					logger.info("it does not, so creating new node.");
					Element node2add = new Element(normalizeName(currentNodeName), namespace);
					if (i == nodes.length - 1)
						node2add.setText(elementText);
					previous.addContent(0, node2add);
					previous = node2add;
				}

			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				logger.error(e.getCause().getMessage());
			}

		}

		return record;

	}

	protected List<String> getKeysBasedOnFrequencies(Map<String, Integer> unsortedMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		logger.info("Sorted Map:" + sortedMap.toString());
		List<String> result = new ArrayList<>(sortedMap.keySet());

		return result;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		// TODO Auto-generated method stub

		Document document;
		try {
			document = (Document) builder.build(file.toFile());
			Element rootNode = document.getRootElement();

			Record record = new Record();
			record.setMetadata(rootNode);
			record.setRecordName(file.getFileName().toString());

			Element chosenElement = JDomUtils.getXpathNode(elementPath, rootNode.getNamespace(), record.getMetadata());

			if (chosenElement != null) {
				logger.info("Found element:" + elementPath);
				String chosenElementText = chosenElement.getText();
				if (!chosenElementText.trim().equals("")) {

					logger.info("Chosen text analyzer:" + analyzerClass);

					Analyzer analyzer = (Analyzer) ClassObjectCreation.createClassObject(analyzerClass);

					Map<String, Integer> tokenFrequencies = algorithm.processInput(analyzer, chosenElementText);
					logger.info("Token frequencies:" + tokenFrequencies.toString());

					List<String> sortedTokens = getKeysBasedOnFrequencies(tokenFrequencies);
					int sortedTokensNumber = sortedTokens.size();
					int elements2import_number = number_of_keywords2insert - sortedTokensNumber;

					if (elements2import_number > 0)
						elements2import_number = sortedTokensNumber;
					else if (elements2import_number <= 0)
						elements2import_number = number_of_keywords2insert;

					Record enrichedRecord = null;
					for (int i = 0; i < elements2import_number; i++) {
						enrichedRecord = appendElement(element2import, Namespace.getNamespace(prefix, nameSpace),
								record, sortedTokens.get(i));

					}
					output.storeKERecord(enrichedRecord, outputProperties);

				} else
					logger.error("Element:" + elementPath + " contains not text");
			} else
				logger.error("Element:" + elementPath + " not found.");
		} catch (JDOMException e) {
			// TODO Auto-generated catch block

			logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			System.exit(-1);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			System.exit(-1);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		System.err.println(file + " could not be processed.");
		return FileVisitResult.CONTINUE;
	}

}
