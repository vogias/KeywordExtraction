package ke.algorithms;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TypicalAlgorithm implements KEAlgorithm {

	@Override
	public Map<String, Integer> processInput(Analyzer analyzer, String inputText) {

		List<String> result = new ArrayList<String>();
		try {
			TokenStream stream = analyzer.tokenStream(null, new HTMLStripCharFilter(new StringReader(inputText)));

			stream.reset();
			while (stream.incrementToken()) {
				result.add(stream.getAttribute(CharTermAttribute.class).toString());
			}
			return CollectionUtils.getCardinalityMap(result);
		} catch (IOException e) {
			// not thrown b/c we're using a string reader...
			throw new RuntimeException(e);
		}

	}

}
