package org.exp.paser;

import org.exp.inter.Access;
import org.exp.inter.Expr;
import org.exp.inter.Id;
import org.exp.inter.Stmt;
import org.exp.inter.logical.Or;
import org.exp.inter.stmt.*;
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

	Stmt stmts() throws IOException {
		if (look.tag == '}')
			return Stmt.Null;
		return new Sequence(stmt(), stmts());
	}

	Stmt stmt() throws IOException {
		Expr x;
		Stmt s, s1, s2;
		Stmt savedStmt;
		switch (look.tag) {
			case ';':
				move();
				return Stmt.Null;
			case Tag.IF:
				match(Tag.IF);
				match('(');
				x = bool();
				match(')');
				s1 = stmt();
				if (look.tag != Tag.ELSE)
					return new If(x, s1);
				match(Tag.ELSE);
				s2 = stmt();
				return new Else(x, s1, s2);
			case Tag.WHILE:
				While whileNode = new While();
				savedStmt = Stmt.Enclosing;
				Stmt.Enclosing = whileNode;
				match(Tag.WHILE);
				match('(');
				x = bool();
				match(')');
				s1 = stmt();
				whileNode.init(x, s1);
				Stmt.Enclosing = savedStmt;
				return whileNode;
			case Tag.DO:
				Do doNode = new Do();
				savedStmt = Stmt.Enclosing;
				Stmt.Enclosing = doNode;
				match(Tag.DO);
				s1 = stmt();
				match(Tag.WHILE);
				match('(');
				x = bool();
				match(')');
				match(';');
				doNode.init(s1, x);
				Stmt.Enclosing = savedStmt;
				return doNode;
			case Tag.BREAK:
				match(Tag.BREAK);
				match(';');
				return new Break();
			case '{':
				return block();
			default:
				return assign();
		}
	}

	Stmt assign() throws IOException {
		Stmt stmt;
		Token t = look;
		match(Tag.ID);
		Id id = top.get(t);
		if (id == null)
			error(t.toString() + " undeclared");
		if (look.tag == '=') {
			move();
			assert id != null; //redundant
			stmt = new Set(id, bool());
		} else {
			Access x = offset(id);
			match('=');
			stmt = new SetElement(x, bool());
		}
		match(';');
		return stmt;
	}

	Expr bool() throws IOException {
		Expr x = join();
		while (look.tag == Tag.OR) {
			Token tok = look;
			move();
			x = new Or(tok, x, join());
		}
		return x;
	}


}
