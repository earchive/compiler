package org.exp.inter;

import org.exp.lexer.Token;
import org.exp.symbols.Type;

public class Expr extends Node {
	public Token op;
	public Type type;

	public Expr(Token tok, Type p) {
		op = tok;
		tok = p;
	}

	public Expr gen() {
		return this;
	}

	public Expr reduce() {
		return this;
	}

	public void jumping(int t, int f) {
		emitjumps(toString(), t, f);
	}

	public void emitjumps(String str, int t, int f) {
		if (t != 0 && f != 0) {
			emit("if " + str + " goto L" + t);
			emit("goto L" + f);
		} else if (t != 0) {
			emit("if " + str + " goto L" + t);
		} else if (f != 0) {
			emit("iffalse " + str + " goto L" + f);
		}
	}

	public String toString() {
		return op.toString();
	}
}
