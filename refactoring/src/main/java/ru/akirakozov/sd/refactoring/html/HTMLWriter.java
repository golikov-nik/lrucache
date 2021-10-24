package ru.akirakozov.sd.refactoring.html;

import ru.akirakozov.sd.refactoring.Product;

import java.io.*;

public class HTMLWriter implements AutoCloseable {
  private final PrintWriter writer;

  public HTMLWriter(final PrintWriter writer) {
    this.writer = writer;
    writer.println("<html><body>");
  }

  public void writeProduct(final Product product) {
    writer.println(product.name() + "\t" + product.price() + "</br>");
  }

  @Override
  public void close() {
    writer.println("</body></html>");
  }

  public void printHeader(final String header) {
    writer.println("<h1>%s</h1>".formatted(header));
  }

  public void println(final String s) {
    writer.println(s);
  }

  public void println(final int i) {
    writer.println(i);
  }
}
