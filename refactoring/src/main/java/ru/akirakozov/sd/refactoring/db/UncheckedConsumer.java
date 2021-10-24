package ru.akirakozov.sd.refactoring.db;

@FunctionalInterface
public interface UncheckedConsumer<T> {
  void accept(final T arg) throws Exception;
}
