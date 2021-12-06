import ru.ifmo.rain.golikov.token.*;
import ru.ifmo.rain.golikov.token.tokenizer.Tokenizer;
import ru.ifmo.rain.golikov.visitor.CalcVisitor;
import ru.ifmo.rain.golikov.visitor.ParserVisitor;
import ru.ifmo.rain.golikov.visitor.PrintVisitor;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    var tokenizer = new Tokenizer(System.in);
    List<Token> tokens = tokenizer.process();
    List<Token> tokensPolish = new ParserVisitor().convertToPolish(tokens);
    new PrintVisitor(System.out).print(tokensPolish);
    System.out.println();
    System.out.println("Result: " + new CalcVisitor().evaluate(tokensPolish));
  }
}
