package ru.akirakozov.sd.refactoring.utils;

@FunctionalInterface
public interface UncheckedConsumer<T> {
  void accept(final T arg) throws Exception;
}
