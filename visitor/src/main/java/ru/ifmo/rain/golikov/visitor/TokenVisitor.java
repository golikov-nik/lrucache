package ru.ifmo.rain.golikov.visitor;

import ru.ifmo.rain.golikov.token.Brace;
import ru.ifmo.rain.golikov.token.NumberToken;
import ru.ifmo.rain.golikov.token.Operation;

public interface TokenVisitor {
  void visit(NumberToken token);
  void visit(Brace token);
  void visit(Operation token);
}
