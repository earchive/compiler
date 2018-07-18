package org.exp.lexer;

import java.util.Hashtable;

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable words = new Hashtable();

	public void reserve(Word d) {
		words.put(d.lexme, d);
	}

	public Lexer() {
		reserve(new Word("if", Tag.IF));
	}
}
