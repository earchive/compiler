package org.exp.lexer;

import java.io.IOException;
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
		reserve(new Word("else", Tag.ELSE));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(Word.True);
		reserve(Word.and);
		reserve(Word.or);
		reserve(Word.eq);
		reserve(Word.ne);
		reserve(Word.le);
		reserve(Word.ge);
		reserve(Word.minus);
		reserve(Word.True);
		reserve(Word.False);
		reserve(Word.temp);
	}

	public void readch() throws IOException {
		peek = (char) System.in.read();
	}

	public boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;
		peek = ' ';
		return true;
	}
}
