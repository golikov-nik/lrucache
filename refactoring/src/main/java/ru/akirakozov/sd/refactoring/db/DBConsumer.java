package ru.akirakozov.sd.refactoring.db;

import java.sql.Statement;

@FunctionalInterface
public interface DBConsumer {
  void apply(final Statement stmt) throws Exception;
}
