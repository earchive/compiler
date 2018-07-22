package org.exp.symbols;

import org.exp.lexer.Token;

import java.util.Hashtable;

public class Env {
	private Hashtable table;
	protected Env prev;

	public Env(Env n) {
		table = new Hashtable();
		prev = n;
	}

	//public void put(Token w, Id i) {

	//}
}
