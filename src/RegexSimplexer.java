import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSimplexer implements Iterator<Token> {

	/*
	 * Regex works for CodeWars tests, has problems with strings such as "truex", can be fixed by only allowing operators such as '(' and
	 * ')' to be attached to booleans. Likewise with integers becoming identifiers if letters are attached. Easily fixed, but makes
	 * getToken() a bit messier as regex pattern groups will change - Not important for testing against Simplexer class.
	 */
	
	private static final Pattern WS_PATTERN = Pattern.compile("(\\s+)(.*)");
	private static final Pattern INT_PATTERN = Pattern.compile("(\\d+)(.*)");
	private static final Pattern BOOL_PATTERN = Pattern.compile("(true|false)(.*)");
	private static final Pattern STR_PATTERN = Pattern.compile("(\"(\\w)*\")(.*)");
	private static final Pattern OP_PATTERN = Pattern.compile("(\\+|\\-|\\*|\\/|\\%|\\(|\\)|\\=)(.*)");
	private static final Pattern KW_PATTERN = Pattern.compile("(if|else|for|while|return|func|break)(.*)");
	private static final Pattern ID_PATTERN = Pattern.compile("((\\w|\\$|\\_)+)((\\s(.*))*)");
	private Matcher m;
	private String buffer;

	public RegexSimplexer(String buffer) {
		this.buffer = buffer;
	}

	public boolean hasNext() {
		return buffer != null && buffer.length() > 0;
	}

	public Token next() {
		if (!this.hasNext())
			throw new NoSuchElementException();

		if (isToken(WS_PATTERN))
			return getToken("whitespace");
		if (isToken(INT_PATTERN))
			return getToken("integer");
		if (isToken(BOOL_PATTERN))
			return getToken("boolean");
		if (isToken(STR_PATTERN))
			return getToken("string");
		if (isToken(OP_PATTERN))
			return getToken("operator");
		if (isToken(KW_PATTERN))
			return getToken("keyword");
		if (isToken(ID_PATTERN))
			return getToken("identifier");

		return new Token(null, null);
	}

	private boolean isToken(Pattern p) {
		m = p.matcher(buffer);
		return m.matches();
	}

	private Token getToken(String type) {
		buffer = (type.equals("identifier") || type.equals("string")) ? m.group(3) : m.group(2);
		return new Token(m.group(1), type);
	}

}
