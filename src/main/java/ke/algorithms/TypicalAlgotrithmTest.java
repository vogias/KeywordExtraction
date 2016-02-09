package ke.algorithms;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypicalAlgotrithmTest {
	private static final Logger logger = LoggerFactory.getLogger(TypicalAlgotrithmTest.class);

	@Test
	public void testTokenizeInputAnalyzerString() {
		KEAlgorithm algotrithm = new TypicalAlgorithm();

		Analyzer analyzer = new EnglishAnalyzer();
		
		Map<String, Integer> processInput = algotrithm.processInput(analyzer, "terminal terminator terminate");

		logger.info(processInput.toString());

	}

}
