package ke.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.algorithms.KEAlgorithm;
import ke.inputs.KEInput;
import ke.outputs.KEOutput;
import ke.processing.ProcessData;
import ke.properties.Configuration;

public class KeywordExtraction {
	private static final Logger logger = LoggerFactory.getLogger(KeywordExtraction.class);

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub

		Configuration configuration = new Configuration();
		String algorithmClass = configuration.getAlgorithmClass();
		String processingClass = configuration.getProcessingClass();
		String inputClass = configuration.getInputClass();
		String outputClass = configuration.getOutputClass();

		KEInput inputData = (KEInput) ClassObjectCreation.createClassObject(inputClass);
		logger.info("Input class chosen is:" + inputClass);

		ProcessData dataProcess = (ProcessData) ClassObjectCreation.createClassObject(processingClass);
		logger.info("Processing class chosen is:" + processingClass);

		KEAlgorithm algorithm = (KEAlgorithm) ClassObjectCreation.createClassObject(algorithmClass);
		logger.info("Algorithm class chosen is:" + algorithmClass);

		KEOutput output = (KEOutput) ClassObjectCreation.createClassObject(outputClass);
		logger.info("Output class chosen is:" + outputClass);

		dataProcess.processData(inputData, algorithm, output, configuration);
	}

}
