package ru.ifmo.rain.golikov.token.tokenizer.state;

import ru.ifmo.rain.golikov.token.Token;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;

import java.io.*;

public abstract class State {
  public abstract Token nextToken(final Tokenizer tokenizer) throws IOException;

  public abstract State nextState(final Tokenizer tokenizer) throws IOException;
}
