package org.exp.inter.logical;

import org.exp.inter.Expr;
import org.exp.inter.Logical;
import org.exp.lexer.Token;

public class And extends Logical {
	public And(Token tok, Expr expr1, Expr expr2) {
		super(tok, expr1, expr2);
	}

	public void jumping(int t, int f) {
		int label = f != 0 ? f : newlabel();
		expr1.jumping(0,label);
		expr2.jumping(t, f);
		if (f == 0)
			emitlabel(label);
	}
}

