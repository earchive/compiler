package org.exp.inter;

import org.exp.lexer.Word;
import org.exp.symbols.Type;

public class Id extends Expr {
	public int offset;

	public Id(Word id, Type type, int b) {
		super(id, type);
		offset = b;
	}
}
