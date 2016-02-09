package ke.analyzers;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class CustomAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String field) {
		// TODO Auto-generated method stub
		final Tokenizer tokens = new StandardTokenizer();
		TokenFilter filter = new StandardFilter(tokens);

		filter = new LowerCaseFilter(filter);
		filter = new StopFilter(filter, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		filter = new LengthFilter(filter, 4, Integer.MAX_VALUE);
		filter = new NumberFilter(filter);
		return new TokenStreamComponents(tokens, filter);
	}

}
