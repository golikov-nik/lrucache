package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.data.Product;
import ru.akirakozov.sd.refactoring.utils.UncheckedConsumer;

import java.sql.ResultSet;

public class DBManager {
  private static final DBClient client = new DBClient();

  public static void initDB() {
    client.executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)");
  }

  public static void dropDB() {
    client.executeUpdate("DROP TABLE PRODUCT");
  }

  public static void addProduct(final Product product) {
    client.executeUpdate("INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")");
  }

  public static void querySumPrice(final UncheckedConsumer<ResultSet> action) {
    client.executeQuery("SELECT SUM(price) FROM PRODUCT", action);
  }

  public static void queryCount(final UncheckedConsumer<ResultSet> action) {
    client.executeQuery("SELECT COUNT(*) FROM PRODUCT", action);
  }

  public static void queryMin(final UncheckedConsumer<ResultSet> action) {
    client.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", action);
  }

  public static void queryMax(final UncheckedConsumer<ResultSet> action) {
    client.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", action);
  }

  public static void queryAll(final UncheckedConsumer<ResultSet> action) {
    client.executeQuery("SELECT * FROM PRODUCT", action);
  }
}
