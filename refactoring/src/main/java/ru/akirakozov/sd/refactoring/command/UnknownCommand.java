package ru.akirakozov.sd.refactoring.command;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class UnknownCommand extends Command {
  private final String command;

  public UnknownCommand(String command) {
    this.command = command;
  }

  @Override
  public void process(final HttpServletResponse response) throws IOException {
    response.getWriter().println("Unknown command: " + command);
  }
}
