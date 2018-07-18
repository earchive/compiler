package org.exp.lexer;

public class Token {
	public final int tag;

	public Token(int tag) {
		this.tag = tag;
	}

	public String toString() {
		return "" + (char) tag;
	}
}
