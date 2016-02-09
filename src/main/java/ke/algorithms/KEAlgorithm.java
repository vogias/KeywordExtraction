package ke.algorithms;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;

public interface KEAlgorithm {

	public Map<String, Integer> processInput(Analyzer analyzer, String inputText);

}
