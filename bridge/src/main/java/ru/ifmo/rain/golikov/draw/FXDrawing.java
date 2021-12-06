package ru.ifmo.rain.golikov.draw;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import ru.ifmo.rain.golikov.geom.Circle;
import ru.ifmo.rain.golikov.geom.Segment;

import java.util.*;

public class FXDrawing implements DrawingApi {
  final private static List<Shape> shapes = new ArrayList<>();

  @Override
  public void drawCircle(final Circle circle) {
    shapes.add(new javafx.scene.shape.Circle(circle.x(), circle.y(), circle.radius()));
  }

  @Override
  public void drawSegment(final Segment segment) {
    shapes.add(new javafx.scene.shape.Line(segment.a().x(), segment.a().y(), segment.b().x(), segment.b().y()));
  }

  @Override
  public void doDraw() {
    Application.launch(FXDrawingApplication.class);
  }

  public static class FXDrawingApplication extends Application {
    @Override
    public void start(final Stage primaryStage) {
      Group root = new Group();
      root.getChildren().addAll(shapes);
      primaryStage.setScene(new Scene(root, DRAWING_AREA_WIDTH, DRAWING_AREA_HEIGHT));
      primaryStage.show();
    }
  }
}
