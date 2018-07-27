package org.exp.inter.stmt;

import org.exp.inter.Expr;
import org.exp.inter.Stmt;
import org.exp.symbols.Type;

public class If extends Stmt {
	Expr expr;
	Stmt stmt;

	public If(Expr x, Stmt s) {
		expr = x;
		stmt = s;
		if (expr.type != Type.Bool)
			expr.error("boolean required in if statement");
	}

	public void gen(int b, int a) {
		int label = newlabel();
		expr.jumping(0, a);
		emitlabel(label);
		stmt.gen(label, a);
	}
}
