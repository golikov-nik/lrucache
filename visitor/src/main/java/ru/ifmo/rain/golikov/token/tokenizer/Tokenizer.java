package ru.ifmo.rain.golikov.token.tokenizer;

import ru.ifmo.rain.golikov.token.Token;
import ru.ifmo.rain.golikov.token.tokenizer.state.End;
import ru.ifmo.rain.golikov.token.tokenizer.state.Error;
import ru.ifmo.rain.golikov.token.tokenizer.state.Start;
import ru.ifmo.rain.golikov.token.tokenizer.state.State;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Tokenizer {
  private static final char EOF = (char) -1;
  private final PushbackReader in;
  private State curState = Start.INSTANCE;

  public Tokenizer(final InputStream in) {
    this.in = new PushbackReader(new InputStreamReader(in, StandardCharsets.UTF_8));
  }

  public List<Token> process() throws IOException {
    var result = new ArrayList<Token>();
    skipWhitespace();
    curState = curState.nextState(this);
    while (curState != End.INSTANCE && curState != Error.INSTANCE) {
      result.add(curState.nextToken(this));
      skipWhitespace();
      curState = curState.nextState(this);
    }
    if (curState == Error.INSTANCE) {
      throw new IllegalArgumentException("Input cannot be tokenized");
    }
    return result;
  }

  public char nextChar() throws IOException {
    return readChar(false);
  }

  private char readChar(boolean unread) throws IOException {
    int nxt = in.read();
    char result = nxt < 0 ? EOF : (char) nxt;
    if (unread) {
      in.unread(nxt);
    }
    return result;
  }

  public char peekChar() throws IOException {
    return readChar(true);
  }

  public boolean isDigit(final char ch) {
    return Character.isDigit(ch);
  }

  public boolean isEnd(final char ch) {
    return ch == EOF;
  }

  public void skipWhitespace() throws IOException {
    while (Character.isWhitespace(peekChar())) {
      nextChar();
    }
  }

  public boolean isBrace(final char ch) {
    return ch == '(' || ch == ')';
  }

  public boolean isOperation(final char ch) {
    return ch == '+' || ch == '-' || ch == '*' || ch == '/';
  }

  public boolean isEnd() throws IOException {
    return isEnd(peekChar());
  }

  public boolean isDigit() throws IOException {
    return isDigit(peekChar());
  }

  public boolean isBrace() throws IOException {
    return isBrace(peekChar());
  }

  public boolean isOperation() throws IOException {
    return isOperation(peekChar());
  }
}
