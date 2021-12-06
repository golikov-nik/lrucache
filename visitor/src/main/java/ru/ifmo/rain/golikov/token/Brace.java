package ru.ifmo.rain.golikov.token;

import ru.ifmo.rain.golikov.visitor.TokenVisitor;

public abstract class Brace implements Token {
  @Override
  public void accept(final TokenVisitor visitor) {
    visitor.visit(this);
  }
}
