package ru.ifmo.rain.golikov.token.tokenizer.state;

import ru.ifmo.rain.golikov.token.NumberToken;
import ru.ifmo.rain.golikov.token.Token;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;

import java.io.*;

public class Number extends State {
  public static final State INSTANCE = new Number();

  private Number() {

  }

  @Override
  public Token nextToken(final Tokenizer tokenizer) throws IOException {
    var sb = new StringBuilder();
    while (tokenizer.isDigit()) {
      sb.append(tokenizer.nextChar());
    }
    return new NumberToken(Long.parseLong(sb.toString()));
  }

  @Override
  public State nextState(final Tokenizer tokenizer) throws IOException {
    return tokenizer.isEnd() ? End.INSTANCE : Start.INSTANCE;
  }
}
