package ru.ifmo.rain.golikov;

import ru.ifmo.rain.golikov.draw.AWTDrawing;
import ru.ifmo.rain.golikov.draw.FXDrawing;
import ru.ifmo.rain.golikov.graph.AdjacencyListGraph;
import ru.ifmo.rain.golikov.graph.AdjacencyMatrixGraph;

import java.io.*;

public class Main {
  private static RuntimeException printUsage() {
    return new IllegalArgumentException("Usage: <fx/awt> <matrix/list> <input file>");
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      throw printUsage();
    }
    final var drawing = switch (args[0]) {
      case "fx" -> new FXDrawing();
      case "awt" -> new AWTDrawing();
      default -> throw printUsage();
    };
    final var graph = switch (args[1]) {
      case "matrix" -> new AdjacencyMatrixGraph(drawing, args[2]);
      case "list" -> new AdjacencyListGraph(drawing, args[2]);
      default -> throw printUsage();
    };
    graph.drawGraph();
  }
}
