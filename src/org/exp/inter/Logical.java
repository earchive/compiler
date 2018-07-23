package org.exp.inter;

import org.exp.lexer.Token;
import org.exp.symbols.Type;

public class Logical extends Expr {
	public Expr expr1, expr2;

	public Logical(Token tok, Expr expr1, Expr expr2) {
		super(tok, null);
		this.expr1 = expr1;
		this.expr2 = expr2;
		type = check(expr1.type, expr2.type);
		if (type == null)
			error("type error");
	}

	public Type check(Type p1, Type p2) {
		if (p1 == Type.Bool && p2 == Type.Bool)
			return Type.Bool;
		return null;
	}

	public Expr gen() {
		int f = newlabel();
		int a = newlabel();
		Temp temp = new Temp(type);
		this.jumping(0, f);
		emit(temp.toString() + " = true");
		emit("goto L" + a);
		emitlabel(f);
		emit(temp.toString() + " = false");
		emitlabel(a);
		return temp;
	}

	public String toString() {
		return expr1.toString() + " " + op.toString() + " " + expr2.toString();
	}
}
