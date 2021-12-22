package ru.ifmo.rain.golikov.statistics;

import ru.ifmo.rain.golikov.clock.Clock;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ClockEventStatistics implements EventStatistics {
  final private Clock clock;
  final private Map<String, List<Instant>> events = new HashMap<>();

  public ClockEventStatistics(final Clock clock) {
    this.clock = clock;
  }

  @Override
  public void incEvent(final String name) {
    events.computeIfAbsent(name, s -> new ArrayList<>()).add(clock.now());
  }

  @Override
  public double getEventStatisticByName(final String name) {
    return getEventStatisticByName(name, clock.now().minus(Duration.ofHours(1)));
  }

  private double getEventStatisticByName(final String name, final Instant after) {
    return events.getOrDefault(name, List.of()).stream().filter(after::isBefore).count() / 60.0;
  }

  @Override
  public Map<String, Double> getAllEventStatistic() {
    final var hourBefore = clock.now().minus(Duration.ofHours(1));

    final var map = new HashMap<String, Double>();
    for (var name : events.keySet()) {
      var rpm = getEventStatisticByName(name, hourBefore);
      if (rpm > 0) {
        map.put(name, rpm);
      }
    }
    return map;
  }

  @Override
  public void printStatistic() {
    for (var event : getAllEventStatistic().entrySet()) {
      System.out.printf("RPM of %s is %f\n", event.getKey(), event.getValue());
    }
  }
}
