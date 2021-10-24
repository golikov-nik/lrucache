package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.html.HTMLWriter;

import static ru.akirakozov.sd.refactoring.db.DBUtils.productFromRS;

public class Max extends HTMLCommand {
  @Override
  protected void processImpl(final HTMLWriter writer) {
    writer.printHeader("Product with max price: ");
    DBManager.queryMax(rs -> writer.writeProduct(productFromRS(rs)));
  }
}
