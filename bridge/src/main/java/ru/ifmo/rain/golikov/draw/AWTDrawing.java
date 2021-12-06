package ru.ifmo.rain.golikov.draw;

import ru.ifmo.rain.golikov.geom.Circle;
import ru.ifmo.rain.golikov.geom.Segment;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class AWTDrawing extends Frame implements DrawingApi {
  final private List<Shape> shapes = new ArrayList<>();

  @Override
  public void drawCircle(final Circle circle) {
    System.err.println(circle);
    shapes.add(new Ellipse2D.Double(
                circle.x() - circle.radius(),
                circle.y() - circle.radius(),
                circle.radius() * 2,
                circle.radius() * 2));
  }

  @Override
  public void drawSegment(final Segment segment) {
    shapes.add(new Line2D.Double(segment.a().x(), segment.a().y(), segment.b().x(), segment.b().y()));
  }

  @Override
  public void paint(final Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getDrawingAreaWidth(), getDrawingAreaHeight());
    g.setColor(Color.BLACK);
    final Graphics2D g2d = (Graphics2D) g;
    shapes.forEach(g2d::draw);
    shapes.forEach(g2d::fill);
  }

  @Override
  public void doDraw() {
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
    setSize(getDrawingAreaWidth(), getDrawingAreaHeight());
    setVisible(true);
  }
}
