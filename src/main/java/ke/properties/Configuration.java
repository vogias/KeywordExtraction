package ke.properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	private String inputClass = null;
	private String algorithmClass = null;
	private String processingClass = null;
	private String outputClass = null;

	private String element2analyze = null;
	private String element2import = null;
	private String elementURI = null;
	private String elementPrefix = null;
	private int keywords2insert = 0;

	private String analyzerClass = null;

	private HashMap<String, String> inputProperties = null;
	private HashMap<String, String> outputProperties = null;
	private Properties props = null;

	public Configuration() {
		props = PropertiesSingleton.getInstance();
		inputClass = props.getProperty(Constants.inputClass);
		element2analyze = props.getProperty(Constants.element2analyze);
		element2import = props.getProperty(Constants.element2import);
		elementURI = props.getProperty(Constants.elementURI);
		elementPrefix = props.getProperty(Constants.elementPrefix);
		analyzerClass = props.getProperty(Constants.analyzerClass);
		try {
			keywords2insert = Integer.parseInt(props.getProperty(Constants.keywords2insert));
		} catch (NumberFormatException ex) {
			logger.error(ex.getMessage());
		}

		algorithmClass = props.getProperty(Constants.algorithmClass);
		processingClass = props.getProperty(Constants.processingClass);
		outputClass = props.getProperty(Constants.outputClass);
		inputProperties = getKeyProperties(Constants.inputParameters);
		outputProperties = getKeyProperties(Constants.outputParameters);

		if (!checkPropertiesIntegrity())
			System.exit(-1);

	}

	public String getAnalyzerClass() {
		return analyzerClass;
	}

	public String getElementPrefix() {
		return elementPrefix;
	}

	public String getElement2import() {
		return element2import;
	}

	public String getProcessingClass() {
		return processingClass;
	}

	public String getElementURI() {
		return elementURI;
	}

	public int getKeywords2insert() {
		return keywords2insert;
	}

	public String getElement2analyze() {
		return element2analyze;
	}

	public String getInputClass() {
		return inputClass;
	}

	public String getAlgorithmClass() {
		return algorithmClass;
	}

	public String getOutputClass() {
		return outputClass;
	}

	public HashMap<String, String> getInputProperties() {
		return inputProperties;
	}

	public HashMap<String, String> getOutputProperties() {
		return outputProperties;
	}

	protected Boolean checkPropertiesIntegrity() {

		inputProperties = getKeyProperties(Constants.inputParameters);
		outputProperties = getKeyProperties(Constants.outputParameters);

		boolean confHealth = true;

		if (inputClass == null || inputClass.trim().equals("")) {

			logger.error("no valid ke.input.class value");
			confHealth = false;
		}
		if (algorithmClass == null || algorithmClass.trim().equals("")) {

			logger.error("no valid ke.processing.algorigthm.class value");
			confHealth = false;
		}
		if (processingClass == null || processingClass.trim().equals("")) {

			logger.error("no valid ke.processing.class value");
			confHealth = false;
		}
		if (outputClass == null || outputClass.trim().equals("")) {

			logger.error("no valid ke.output.class value");
			confHealth = false;
		}
		if (analyzerClass == null || analyzerClass.trim().equals("")) {

			logger.error("no valid ke.processing.analyzer value");
			confHealth = false;
		}
		if (inputProperties == null || inputProperties.isEmpty()) {

			logger.error("no valid ke.input.parameter value");
			confHealth = false;
		}
		if (outputProperties == null || outputProperties.isEmpty()) {

			logger.error("no valid ke.output.parameter value");
			confHealth = false;
		}

		if (element2analyze == null || element2analyze.trim().equals("")) {

			logger.error("no valid ke.element.input value");
			confHealth = false;
		}
		if (element2import == null || element2import.trim().equals("")) {

			logger.error("no valid ke.element.output value");
			confHealth = false;
		}
		if (elementURI == null || elementURI.trim().equals("")) {

			logger.error("no valid ke.schema.uri value");
			confHealth = false;
		}
		if (elementPrefix == null || elementPrefix.trim().equals("")) {

			logger.error("no valid ke.element.prefix value");
			confHealth = false;
		}

		if (keywords2insert == 0) {

			logger.error("no valid ke.keywords.number value");
			confHealth = false;
		}
		if (confHealth)
			logger.info("Config file is ok.");

		return confHealth;
	}

	private HashMap<String, String> getKeyProperties(String propertyName) {

		HashMap<String, String> propertiesBundle = new HashMap<>();
		for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = props.getProperty(name);
			if (name.startsWith(propertyName)) {
				propertiesBundle.put(name, value);
			}
		}
		return propertiesBundle;
	}

}
