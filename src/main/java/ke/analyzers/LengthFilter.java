package ke.analyzers;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.FilteringTokenFilter;

public class LengthFilter extends FilteringTokenFilter {

	private final int min;
	private final int max;

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

	/**
	 * Create a new LengthFilter. This will filter out tokens whose
	 * CharTermAttribute is either too short (< min) or too long (> max).
	 * 
	 * @param version
	 *            the Lucene match version
	 * @param in
	 *            the TokenStream to consume
	 * @param min
	 *            the minimum length
	 * @param max
	 *            the maximum length
	 */
	public LengthFilter(TokenStream in, int min, int max) {
		super(in);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean accept() {
		final int len = termAtt.length();
		return (len >= min && len <= max);
	}

}
