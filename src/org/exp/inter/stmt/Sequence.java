package org.exp.inter.stmt;

import org.exp.inter.Stmt;

public class Sequence extends Stmt {
	Stmt stmt1;
	Stmt stmt2;

	public Sequence(Stmt stmt1, Stmt stmt2) {
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;
	}

	public void gen(int b, int a) {
		if (stmt1 == Stmt.Null)
			stmt2.gen(b, a);
		else if (stmt2 == Stmt.Null)
			stmt1.gen(b, a);
		else {
			int label = newlabel();
			stmt1.gen(b, label);
			emitlabel(label);
			stmt2.gen(label, a);
		}
	}
}
