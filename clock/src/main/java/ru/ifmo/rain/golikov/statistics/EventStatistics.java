package ru.ifmo.rain.golikov.statistics;

import java.util.*;

public interface EventStatistics {
  void incEvent(String name);
  double getEventStatisticByName(String name);
  Map<String, Double> getAllEventStatistic();
  void printStatistic();
}
