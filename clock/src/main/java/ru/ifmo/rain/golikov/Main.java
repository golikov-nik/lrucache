package ru.ifmo.rain.golikov;

import ru.ifmo.rain.golikov.clock.NormalClock;
import ru.ifmo.rain.golikov.statistics.ClockEventStatistics;

public class Main {
  public static void main(String[] args) {
    final var statistics = new ClockEventStatistics(new NormalClock());
    statistics.incEvent("a");
    statistics.incEvent("b");
    statistics.incEvent("a");
    statistics.printStatistic();
  }
}
