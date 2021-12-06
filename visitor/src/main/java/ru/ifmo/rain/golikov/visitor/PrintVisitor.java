package ru.ifmo.rain.golikov.visitor;

import ru.ifmo.rain.golikov.token.Brace;
import ru.ifmo.rain.golikov.token.NumberToken;
import ru.ifmo.rain.golikov.token.Operation;
import ru.ifmo.rain.golikov.token.Token;

import java.io.*;
import java.util.*;

public class PrintVisitor implements TokenVisitor {
  private final PrintStream out;

  public PrintVisitor(final OutputStream out) {
    this.out = new PrintStream(out);
  }

  @Override
  public void visit(final NumberToken token) {
    out.print(token + " ");
  }

  @Override
  public void visit(final Brace token) {
    out.print(token + " ");
  }

  @Override
  public void visit(final Operation token) {
    out.print(token + " ");
  }

  public void print(final List<Token> tokens) {
    tokens.forEach(t -> t.accept(this));
  }
}
