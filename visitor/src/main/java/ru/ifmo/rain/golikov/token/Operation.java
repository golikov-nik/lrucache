package ru.ifmo.rain.golikov.token;

import ru.ifmo.rain.golikov.visitor.TokenVisitor;

public abstract class Operation implements Token {
  public abstract int priority();

  @Override
  public void accept(final TokenVisitor visitor) {
    visitor.visit(this);
  }

  public abstract long apply(final long a, final long b);
}
