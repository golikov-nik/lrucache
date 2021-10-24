package ru.akirakozov.sd.refactoring.command;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public abstract class Command {
  public abstract void process(final HttpServletResponse response) throws IOException;
}
