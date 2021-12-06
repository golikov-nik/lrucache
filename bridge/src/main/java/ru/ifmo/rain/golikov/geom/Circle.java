package ru.ifmo.rain.golikov.geom;

public record Circle(Point center, double radius) {
  public double x() {
    return center.x();
  }

  public double y() {
    return center.y();
  }

}
