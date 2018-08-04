package org.exp.inter.stmt;

import org.exp.inter.Access;
import org.exp.inter.Expr;
import org.exp.inter.Id;
import org.exp.inter.Stmt;
import org.exp.symbols.Array;
import org.exp.symbols.Type;

public class SetElement extends Stmt {
	public Id array;
	public Expr index;
	public Expr expr;

	public SetElement(Access x, Expr y) {
		array = x.array;
		index = x.index;
		expr = y;
		if (check(x.type, expr.type) == null)
			error("type error");
	}

	public Type check(Type p1, Type p2) {
		if (p1 instanceof Array || p2 instanceof Array)
			return null;
		else if (p1 == p2)
			return p2;
		else if (Type.numeric(p1) && Type.numeric(p2))
			return p2;
		return null;
	}

	public void gen(int b, int a) {
		emit(array.toString() + " [ " + index.reduce().toString() + " ] " + expr.reduce().toString());
	}
}
