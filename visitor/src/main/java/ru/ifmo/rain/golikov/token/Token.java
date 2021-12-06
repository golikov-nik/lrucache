package ru.ifmo.rain.golikov.token;

import ru.ifmo.rain.golikov.visitor.TokenVisitor;

public interface Token {
  void accept(TokenVisitor visitor);
}
