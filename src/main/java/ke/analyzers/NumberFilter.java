package ke.analyzers;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public class NumberFilter extends FilteringTokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

	/**
	 * Create a new NumberFilter. This will filter out tokens whose
	 * CharTermAttribute is a number.
	 * 
	 * @param version
	 *            the Lucene match version
	 * @param in
	 *            the TokenStream to consume
	 * 
	 */
	public NumberFilter(TokenStream in) {
		super(in);

	}

	@Override
	public boolean accept() {
		final String term = termAtt.toString();
		try {
			Integer.parseInt(term);
			return false;
		} catch (NumberFormatException ex) {
			return true;
		}

	}

}
