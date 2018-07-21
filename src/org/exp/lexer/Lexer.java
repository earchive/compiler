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

	public Token scan() throws IOException {
		for (; ; readch()) {
			if (peek == ' ' || peek == '\t') continue;
			else if (peek == '\n') line++;
			else break;
		}
		switch (peek) {
			case '&':
				if (readch('&')) return Word.and;
				else return new Token('&');
			case '|':
				if (readch('|')) return Word.or;
				else return new Token('|');
			case '=':
				if (readch('=')) return Word.eq;
				else return new Token('=');
			case '!':
				if (readch('=')) return Word.ne;
				else return new Token('!');
			case '<':
				if (readch('=')) return Word.le;
				else return new Token('<');
			case '>':
				if (readch('=')) return Word.ge;
				else return new Token('>');
		}
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			if (peek != ',') return new Num(v);
			double x = v;
			double d = 10D;
			for (; ; ) {
				readch();
				if (!Character.isDigit(peek)) break;
				x += Character.digit(peek, 10) / d;
				d *= 10;
			}
			return new Real(x);
		}
		if (Character.isLetter(peek)) {
			StringBuilder b = new StringBuilder();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null) return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
}
