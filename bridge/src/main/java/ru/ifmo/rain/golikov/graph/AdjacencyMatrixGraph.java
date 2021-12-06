package ru.ifmo.rain.golikov.graph;

import ru.ifmo.rain.golikov.draw.DrawingApi;
import ru.ifmo.rain.golikov.graph.base.Edge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AdjacencyMatrixGraph extends Graph {
  final List<List<Boolean>> matrix;

  public AdjacencyMatrixGraph(final DrawingApi drawingApi, final List<List<Boolean>> matrix) {
    super(drawingApi);
    this.matrix = matrix;
  }

  public AdjacencyMatrixGraph(final DrawingApi drawingApi, final String inputFile) throws IOException {
    this(drawingApi, Files.readAllLines(Path.of(inputFile))
            .stream()
            .map(s -> Arrays.stream(s.split(" ")).map(x -> !x.equals("0")).collect(Collectors.toList()))
            .collect(Collectors.toList()));
  }

  @Override
  protected int countVertices() {
    return matrix.size();
  }

  @Override
  protected void forEachEdge(final Consumer<Edge> action) {
    for (int v = 0; v < matrix.size(); v++) {
      for (int u = 0; u < matrix.size(); u++) {
        if (matrix.get(v).get(u)) {
          action.accept(new Edge(v, u));
        }
      }
    }
  }
}
