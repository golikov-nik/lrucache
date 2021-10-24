package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.Product;

import static ru.akirakozov.sd.refactoring.db.DBClient.executeUpdate;

public class DBManager {
  public static void initDB() {
    executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)");
  }

  public static void dropDB() {
    executeUpdate("DROP TABLE PRODUCT");
  }

  public static void addProduct(final Product product) {
    executeUpdate("INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")");
  }
}
