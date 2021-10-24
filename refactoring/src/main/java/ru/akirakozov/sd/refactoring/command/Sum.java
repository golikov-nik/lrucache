package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

public class Sum extends HTMLCommand {
  @Override
  protected void processImpl(final HTMLWriter writer) {
    writer.println("Summary price: ");
    DBManager.querySumPrice(rs -> writer.println(rs.getInt(1)));
  }
}
