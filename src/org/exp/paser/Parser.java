package org.exp.paser;

import org.exp.inter.Expr;
import org.exp.inter.Id;
import org.exp.inter.Stmt;
import org.exp.inter.stmt.Sequence;
import org.exp.lexer.*;
import org.exp.symbols.Array;
import org.exp.symbols.Env;
import org.exp.symbols.Type;

import java.io.IOException;

public class Parser {
	private Lexer lex;
	private Token look;
	Env top = null;
	int used = 0;

	public Parser(Lexer l) throws IOException {
		lex = l;
		move();
	}

	void move() throws IOException {
		look = lex.scan();
	}

	void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
	}

	void match(int t) throws IOException {
		if (look.tag == t)
			move();
		else error("syntax error");
	}

	public void program() throws IOException {
		Stmt s = block();
		int begin = s.newlabel();
		int after = s.newlabel();
		s.emitlabel(begin);
		s.gen(begin, after);
	}

	Stmt block() throws IOException {
		match('{');
		Env savedEnv = top;
		top = new Env(top);
		decls();
		Stmt s = stmts();
		match('}');
		top = savedEnv;
		return s;
	}

	void decls() throws IOException {
		while (look.tag == Tag.BASIC) {
			Type p = type();
			Token tok = look;
			match(Tag.ID);
			match(';');
			Id id = new Id((Word) tok, p, used);
			top.put(tok, id);
			used += p.width;
		}
	}

	Type type() throws IOException {
		Type p = (Type) look;
		match(Tag.BASIC);
		if (look.tag != '[')
			return p;
		return dims(p);
	}

	Type dims(Type p) throws IOException {
		match('[');
		Token tok = look;
		match(Tag.NUM);
		match(']');
		if (look.tag == '[')
			p = dims(p);
		return new Array(((Num) tok).value, p);
	}

	Stmt stmts() {
		if (look.tag == '}')
			return Stmt.Null;
		return new Sequence(stmt(), stmts());
	}

	Stmt stmt() {
		Expr x;
		Stmt s, s1, s2;
		Stmt savedStmt;


		return new Stmt();
	}
}
