package ru.ifmo.rain.golikov.token.tokenizer.state;

import ru.ifmo.rain.golikov.token.*;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;

import java.io.*;

public class Start extends State {
  public static final State INSTANCE = new Start();

  private Start() {

  }

  @Override
  public Token nextToken(final Tokenizer tokenizer) throws IOException {
    return switch (tokenizer.nextChar()) {
      case '(' -> new Left();
      case ')' -> new Right();
      case '+' -> new Plus();
      case '-' -> new Minus();
      case '*' -> new Mul();
      case '/' -> new Div();
      default -> throw new RuntimeException("Input cannot be tokenized");
    };
  }

  public State nextState(final Tokenizer tokenizer) throws IOException {
    tokenizer.skipWhitespace();
    if (tokenizer.isDigit()) {
      return Number.INSTANCE;
    } else if (tokenizer.isEnd()) {
      return End.INSTANCE;
    } else if (tokenizer.isBrace() || tokenizer.isOperation()) {
      return Start.INSTANCE;
    } else {
      return Error.INSTANCE;
    }
  }
}
