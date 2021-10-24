package ru.akirakozov.sd.refactoring.db;

import java.sql.DriverManager;

public class DBManager {
  public static final String DB_URL = "jdbc:sqlite:test.db";

  public static void withDatabase(final DBConsumer action) {
    try {
      try (var c = DriverManager.getConnection(DB_URL); var stmt = c.createStatement()) {
        action.apply(stmt);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void executeUpdate(final String sql) {
    withDatabase(stmt -> stmt.executeUpdate(sql));
  }
}
