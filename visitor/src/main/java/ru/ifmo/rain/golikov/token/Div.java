package ru.ifmo.rain.golikov.token;

public class Div extends Operation {
  @Override
  public String toString() {
    return "DIV";
  }

  @Override
  public int priority() {
    return 1;
  }

  @Override
  public long apply(final long a, final long b) {
    return a / b;
  }
}
