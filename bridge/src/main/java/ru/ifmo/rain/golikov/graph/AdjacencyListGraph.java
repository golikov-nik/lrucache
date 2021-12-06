package ru.ifmo.rain.golikov.graph;

import ru.ifmo.rain.golikov.draw.DrawingApi;
import ru.ifmo.rain.golikov.graph.base.Edge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AdjacencyListGraph extends Graph {
  final List<List<Integer>> graph;

  public AdjacencyListGraph(final DrawingApi drawingApi, final List<List<Integer>> graph) {
    super(drawingApi);
    this.graph = graph;
  }

  public AdjacencyListGraph(final DrawingApi drawingApi, final String inputFile) throws IOException {
    this(drawingApi, Files.readAllLines(Path.of(inputFile))
            .stream()
            .map(s -> s.isBlank() ? List.<Integer>of() : Arrays.stream(s.split(" ")).map(Integer::valueOf).collect(Collectors.toList()))
            .collect(Collectors.toList()));
  }

  @Override
  protected int countVertices() {
    return graph.size();
  }

  @Override
  protected void forEachEdge(final Consumer<Edge> action) {
    for (int v = 0; v < graph.size(); v++) {
      for (int u : graph.get(v)) {
        action.accept(new Edge(v, u));
      }
    }
  }
}
