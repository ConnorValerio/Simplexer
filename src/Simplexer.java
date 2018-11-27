import java.util.Iterator;
import java.util.NoSuchElementException;

public class Simplexer implements Iterator<Token> {

	/* Lexer without regex */

	private static final String[] operators = {"+", "-", "*", "/", "%", "(", ")", "="};
	private static final String[] keywords = {"if", "else", "for", "while", "return", "func", "break"};
	private String buffer;

	public Simplexer(String buffer) {
		this.buffer = (buffer == null) ? "" : buffer;
	}

	public boolean hasNext() {
		if (buffer.length() > 0)
			return true;
		return false;
	}

	public Token next() {
		if (buffer.length() == 0)
			throw new NoSuchElementException();

		// handle whitespace
		if (buffer.startsWith(" ")) {
			String ws = "";
			for (char c : buffer.toCharArray()) {
				if (Character.isWhitespace(c)) {
					ws = ws.concat(String.valueOf(c));
				} else {
					break;
				}
			}
			this.updateBuffer(ws);
			return new Token(ws, "whitespace");
		}

		// get the first char in the buffer
		String ts = String.valueOf(buffer.charAt(0));

		do {

			// check for tokens
			if (this.isInteger(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "integer");
			}
			if (this.isBoolean(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "boolean");
			}
			if (this.isString(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "string");
			}
			if (this.isOperator(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "operator");
			}
			if (this.isKeyword(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "keyword");
			}
			if (this.isIdentifier(ts) && this.nextIterationIsNotAToken(ts)) {
				this.updateBuffer(ts);
				return new Token(ts, "identifier");
			}

			// increment the token string
			ts = buffer.substring(0, ts.length() + 1);

		} while (ts.length() <= buffer.length());

		// no token found
		return new Token(ts, null);
	}

	private boolean isInteger(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	private boolean isBoolean(String str) {
		if (str.equals("true") || str.equals("false"))
			return true;
		return false;
	}

	private boolean isString(String str) {
		if (str.length() <= 1)
			return false;
		if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"')
			return true;
		return false;
	}

	private boolean isOperator(String str) {
		for (String op : operators) {
			if (op.equals(str))
				return true;
		}
		return false;
	}

	private boolean isKeyword(String str) {
		for (String kw : keywords) {
			if (kw.equals(str))
				return true;
		}
		return false;
	}

	private boolean isIdentifier(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '$' && c != '_') {
				return false;
			}
		}
		return true;
	}

	// updates the buffer by removing the token
	private void updateBuffer(String str) {
		buffer = buffer.substring(str.length());
	}

	/* returns true if the next iteration of the token string is not a token, false otherwise */
	private boolean nextIterationIsNotAToken(String tokenString) {
		if (tokenString.length() == buffer.length())
			return true;

		String nxt = buffer.substring(0, tokenString.length() + 1);
		if (!this.isInteger(nxt) && !this.isBoolean(nxt) && !this.isString(nxt) && !this.isOperator(nxt) && !this.isKeyword(nxt)
				&& !this.isIdentifier(nxt))
			return true;
		return false;
	}

}