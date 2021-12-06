package ru.ifmo.rain.golikov.token;

import ru.ifmo.rain.golikov.visitor.TokenVisitor;

public class NumberToken implements Token {
  private final long value;

  public NumberToken(final long value) {
    this.value = value;
  }

  @Override
  public void accept(final TokenVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "NUMBER(%d)".formatted(value);
  }

  public long getValue() {
    return value;
  }
}
