package org.exp.inter;

import org.exp.lexer.Lexer;

public class Node {
	int lexline;

	public Node() {
		lexline = Lexer.line;
	}

	public void error(String str) {
		throw new Error("near line " + lexline + ": " + str);
	}

	static int labels = 0;

	public int newlabel() {
		return ++labels;
	}

	public void emitlabel(int i) {
		System.out.print("L" + i + ":");
	}

	public void emit(String str) {
		System.out.println("\t" + str);
	}
}
