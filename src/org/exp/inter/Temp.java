package org.exp.inter;

import org.exp.lexer.Word;
import org.exp.symbols.Type;

public class Temp extends Expr {
	static int count = 0;
	int number = 0;

	public Temp(Type p) {
		super(Word.temp, p);
		number = ++count;
	}

	public String toString() {
		return "t" + number;
	}
}
