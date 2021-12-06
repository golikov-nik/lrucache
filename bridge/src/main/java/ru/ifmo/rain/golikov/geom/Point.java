package ru.ifmo.rain.golikov.geom;

public record Point(double x, double y) {
  public double distance(Point other) {
    return Math.hypot(x - other.x(), y - other.y());
  }
}
