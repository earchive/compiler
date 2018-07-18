package org.exp.lexer;

public class Real extends Token {
	public final double value;

	public Real(double d) {
		super(Tag.REAL);
		value = d;
	}

	public String toString() {
		return "" + value;
	}
}
