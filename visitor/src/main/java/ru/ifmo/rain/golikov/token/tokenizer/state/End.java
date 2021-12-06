package ru.ifmo.rain.golikov.token.tokenizer.state;

import ru.ifmo.rain.golikov.token.Token;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;

public class End extends State {
  public static final State INSTANCE = new End();

  private End() {

  }

  @Override
  public Token nextToken(final Tokenizer tokenizer) {
    throw new RuntimeException("Next token called on End state");
  }

  @Override
  public State nextState(final Tokenizer tokenizer) {
    return End.INSTANCE;
  }
}
