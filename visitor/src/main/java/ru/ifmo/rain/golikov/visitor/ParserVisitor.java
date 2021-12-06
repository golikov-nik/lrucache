package ru.ifmo.rain.golikov.visitor;

import ru.ifmo.rain.golikov.token.*;

import java.util.*;
import java.util.function.*;

public class ParserVisitor implements TokenVisitor {
  private final Deque<Token> stack = new ArrayDeque<>();
  private final List<Token> output = new ArrayList<>();

  @Override
  public void visit(final NumberToken token) {
    output.add(token);
  }

  @Override
  public void visit(final Brace token) {
    if (token instanceof Left) {
      stack.addFirst(token);
      return;
    }
    clearStack(t -> !(t instanceof Left));
    if (stack.isEmpty() || !(stack.peekFirst() instanceof Left)) {
      throw new IllegalArgumentException("Input is incorrect infix expression");
    }
    stack.removeFirst();
  }

  @Override
  public void visit(final Operation token) {
    clearStack(t -> t instanceof Operation && ((Operation) t).priority() >= token.priority());
    stack.addFirst(token);
  }

  private void clearStack(Predicate<Token> condition) {
    while (!stack.isEmpty() && condition.test(stack.peekFirst())) {
      output.add(stack.removeFirst());
    }
  }

  public List<Token> convertToPolish(final List<Token> tokens) {
    stack.clear();
    output.clear();
    tokens.forEach(t -> t.accept(this));

    clearStack(t -> {
      if (!(t instanceof Operation)) {
        throw new IllegalArgumentException("Input is incorrect infix expression");
      }
      return true;
    });

    return List.copyOf(output);
  }
}
