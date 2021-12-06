package ru.ifmo.rain.golikov.token;

public class Plus extends Operation {
  @Override
  public String toString() {
    return "PLUS";
  }

  @Override
  public int priority() {
    return 0;
  }

  @Override
  public long apply(final long a, final long b) {
    return a + b;
  }
}
