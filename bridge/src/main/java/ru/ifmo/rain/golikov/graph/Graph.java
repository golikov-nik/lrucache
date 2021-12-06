package ru.ifmo.rain.golikov.graph;

import ru.ifmo.rain.golikov.draw.DrawingApi;
import ru.ifmo.rain.golikov.geom.Circle;
import ru.ifmo.rain.golikov.geom.Point;
import ru.ifmo.rain.golikov.geom.Segment;
import ru.ifmo.rain.golikov.graph.base.Edge;

import java.util.function.*;

public abstract class Graph {
  private final DrawingApi drawingApi;

  public Graph(DrawingApi drawingApi) {
    this.drawingApi = drawingApi;
  }

  private Point getVertex(int v) {
    int n = countVertices();
    double angle = 2 * Math.PI * v / n;
    double xm = drawingApi.getDrawingAreaWidth() / 2.0;
    double ym = drawingApi.getDrawingAreaHeight() / 2.0;
    double radius = Math.min(xm, ym) * 0.7;
    return new Point(xm + Math.cos(angle) * radius, ym + Math.sin(angle) * radius);
  }

  private double getVertexRadius() {
    return getVertex(0).distance(getVertex(1)) / 8;
  }

  private void drawVertex(int v) {
    drawingApi.drawCircle(new Circle(getVertex(v), getVertexRadius()));
  }

  private void drawEdge(Edge e) {
    drawingApi.drawSegment(new Segment(getVertex(e.from()), getVertex(e.to())));
  }

  protected abstract int countVertices();

  protected abstract void forEachEdge(Consumer<Edge> action);

  public void drawGraph() {
    int n = countVertices();
    for (int v = 0; v < n; v++) {
      drawVertex(v);
    }
    forEachEdge(this::drawEdge);
    drawingApi.doDraw();
  }
}
