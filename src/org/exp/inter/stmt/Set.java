package org.exp.inter.stmt;

import org.exp.inter.Expr;
import org.exp.inter.Id;
import org.exp.inter.Stmt;
import org.exp.symbols.Type;

public class Set extends Stmt {
	public Id id;
	public Expr expr;

	public Set(Id id, Expr expr) {
		this.id = id;
		this.expr = expr;
		if (check(id.type, expr.type) == null)
			error("type error");
	}

	public Type check(Type p1, Type p2) {
		if (Type.numeric(p1) && Type.numeric(p2))
			return p2;
		else if (p1 == Type.Bool && p2 == Type.Bool)
			return p2;
		return null;
	}

	public void gen(int b, int a) {
		emit(id.toString() + " = " + expr.gen().toString());
	}
}
