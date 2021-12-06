package ru.ifmo.rain.golikov.token.tokenizer.state;

import ru.ifmo.rain.golikov.token.Token;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;

public class Error extends State {
  public static final State INSTANCE = new Error();

  private Error() {

  }

  @Override
  public Token nextToken(final Tokenizer tokenizer) {
    throw new RuntimeException("Next token called in error state");
  }

  @Override
  public State nextState(final Tokenizer tokenizer) {
    return Error.INSTANCE;
  }
}
