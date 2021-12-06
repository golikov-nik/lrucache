package ru.ifmo.rain.golikov.token;

public class Mul extends Operation {
  @Override
  public String toString() {
    return "MUL";
  }

  @Override
  public int priority() {
    return 1;
  }

  @Override
  public long apply(final long a, final long b) {
    return a * b;
  }
}
