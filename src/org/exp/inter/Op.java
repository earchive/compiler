package org.exp.inter;

import org.exp.lexer.Token;
import org.exp.symbols.Type;

public class Op extends Expr{
	public Op(Token tok, Type p) {
		super(tok, p);
	}

	public Expr reduce() {
		Expr x = gen();
		Temp t = new Temp(type);
		emit(t.toString() + " = " + x.toString());
		return t;
	}
}
