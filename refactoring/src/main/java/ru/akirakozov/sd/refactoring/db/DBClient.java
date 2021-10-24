package ru.akirakozov.sd.refactoring.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class DBClient {
  public static final String DB_URL = "jdbc:sqlite:test.db";

  private void withDatabase(final UncheckedConsumer<Statement> action) {
    try {
      try (var c = DriverManager.getConnection(DB_URL); var stmt = c.createStatement()) {
        action.accept(stmt);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void executeUpdate(final String sql) {
    withDatabase(stmt -> stmt.executeUpdate(sql));
  }

  public void executeQuery(final String sql,
                                  final UncheckedConsumer<ResultSet> action) {
    withDatabase(stmt -> {
      try (var rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
          action.accept(rs);
        }
      }
    });
  }
}
