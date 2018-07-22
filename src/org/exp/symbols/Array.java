package org.exp.symbols;

import org.exp.lexer.Tag;

public class Array extends Type {
	public Type of;
	public int size;

	public Array(int size, Type p) {
		super("[]", Tag.INDEX, size * p.width);
		this.size = size;
		of = p;
	}

	public String toSring() {
		return "[" + size + "]" + of.toString();
	}
}
