package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public abstract class HTMLCommand extends Command {
  @Override
  public void process(final HttpServletResponse response) throws IOException {
    try (var writer = new HTMLWriter(response.getWriter())) {
      processImpl(writer);
    }
  }

  protected abstract void processImpl(final HTMLWriter writer);
}
