package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

public class Count extends HTMLCommand {
  @Override
  protected void processImpl(final HTMLWriter writer) {
    writer.println("Number of products: ");
    DBManager.queryCount(rs -> writer.println(rs.getInt(1)));
  }
}
