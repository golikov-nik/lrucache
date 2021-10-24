package ru.akirakozov.sd.refactoring.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBClient {
  public static final String DB_URL = "jdbc:sqlite:test.db";

  public static void withDatabase(final UncheckedConsumer<Statement> action) {
    try {
      try (var c = DriverManager.getConnection(DB_URL); var stmt = c.createStatement()) {
        action.accept(stmt);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void executeUpdate(final String sql) {
    withDatabase(stmt -> stmt.executeUpdate(sql));
  }

  public static void executeQuery(final String sql,
                                  final UncheckedConsumer<ResultSet> before,
                                  final UncheckedConsumer<ResultSet> processEach,
                                  final UncheckedConsumer<ResultSet> after) {
    withDatabase(stmt -> {
      try (var rs = stmt.executeQuery(sql)) {
        before.accept(rs);
        while (rs.next()) {
          processEach.accept(rs);
        }
        after.accept(rs);
      }
    });
  }
}
