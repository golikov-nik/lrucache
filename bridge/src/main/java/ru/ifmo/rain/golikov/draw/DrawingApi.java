package ru.ifmo.rain.golikov.draw;

import ru.ifmo.rain.golikov.geom.Circle;
import ru.ifmo.rain.golikov.geom.Segment;

public interface DrawingApi {
  int DRAWING_AREA_WIDTH = 600;
  int DRAWING_AREA_HEIGHT = 400;

  default int getDrawingAreaWidth() {
    return DRAWING_AREA_WIDTH;
  }
  default int getDrawingAreaHeight() {
    return DRAWING_AREA_HEIGHT;
  }

  void drawCircle(Circle circle);
  void drawSegment(Segment segment);
  void doDraw();
}
