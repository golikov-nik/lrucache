package ru.ifmo.rain.golikov.visitor;

import ru.ifmo.rain.golikov.token.Brace;
import ru.ifmo.rain.golikov.token.NumberToken;
import ru.ifmo.rain.golikov.token.Operation;
import ru.ifmo.rain.golikov.token.Token;

import java.util.*;

public class CalcVisitor implements TokenVisitor {
  Deque<Long> stack = new ArrayDeque<>();

  @Override
  public void visit(final NumberToken token) {
    stack.addFirst(token.getValue());
  }

  @Override
  public void visit(final Brace token) {
    throw new IllegalArgumentException("Input is incorrect polish notation expression");
  }

  @Override
  public void visit(final Operation token) {
    if (stack.size() < 2) {
      throw new IllegalArgumentException("Input is incorrect polish notation expression");
    }
    long b = stack.removeFirst();
    long a = stack.removeFirst();
    stack.add(token.apply(a, b));
  }

  public long evaluate(final List<Token> tokens) {
    stack.clear();
    tokens.forEach(t -> t.accept(this));
    if (stack.size() != 1) {
      throw new IllegalArgumentException("Input is incorrect polish notation expression");
    }
    return stack.removeFirst();
  }
}
