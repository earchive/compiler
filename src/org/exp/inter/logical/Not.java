package org.exp.inter.logical;

import org.exp.inter.Expr;
import org.exp.inter.Logical;
import org.exp.lexer.Token;

public class Not extends Logical {
	public Not(Token tok, Expr x) {
		super(tok, x, x);
	}

	public void jumping(int t, int f) {
		expr2.jumping(f, t);
	}

	public String toString() {
		return op.toString() + " " + expr2.toString();
	}
}
