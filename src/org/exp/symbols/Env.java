package org.exp.symbols;

import org.exp.inter.Id;
import org.exp.lexer.Token;

import java.util.Hashtable;

public class Env {
	private Hashtable table;
	protected Env prev;

	public Env(Env n) {
		table = new Hashtable();
		prev = n;
	}

	public void put(Token w, Id i) {
		table.put(w, i);
	}

	public Id get(Token w) {
		for (Env e = this; e != null; e = e.prev) {
			Id found = (Id) (e.table.get(w));
			if (found != null)
				return found;
		}
		return null;
	}
}
