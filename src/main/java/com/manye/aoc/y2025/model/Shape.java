package com.manye.aoc.y2025.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Shape {
  private final int id;
  private final Set<Point> points; // Relative coordinates of '#'
  private final int height;
  private final int width;

  public Shape(int id, Set<Point> points) {
    this.id = id;
    // Normalize: Shift points so the top-left-most '#' is at (0,0)
    int minR = points.stream().mapToInt(Point::row).min().orElse(0);
    int minC = points.stream().mapToInt(Point::column).min().orElse(0);

    this.points = points.stream()
        .map(p -> new Point(p.row() - minR, p.column() - minC))
        .collect(Collectors.toUnmodifiableSet());

    this.height = this.points.stream().mapToInt(Point::row).max().orElse(0) + 1;
    this.width = this.points.stream().mapToInt(Point::column).max().orElse(0) + 1;
  }

  public int area() {
    return points.size();
  }

  // --- Variations Generation ---

  public List<Shape> generateVariations() {
    Set<Set<Point>> seen = new HashSet<>();
    List<Shape> variations = new ArrayList<>();

    Shape current = this;
    // 1. Rotate 4 times
    for (int i = 0; i < 4; i++) {
      addIfNew(current, seen, variations);
      // 2. Flip horizontal and add
      addIfNew(current.flip(), seen, variations);

      current = current.rotate();
    }
    return variations;
  }

  private void addIfNew(Shape s, Set<Set<Point>> seen, List<Shape> variations) {
    if (seen.add(s.points)) {
      variations.add(s);
    }
  }

  private Shape rotate() {
    // 90 degrees clockwise: (row, column) -> (column, -row)
    return new Shape(this.id, points.stream()
        .map(p -> new Point(p.column(), -p.row()))
        .collect(Collectors.toSet()));
  }

  private Shape flip() {
    // Flip horizontal: (row, column) -> (row, -column)
    return new Shape(this.id, points.stream()
        .map(p -> new Point(p.row(), -p.column()))
        .collect(Collectors.toSet()));
  }

  public Set<Point> points() { return points; }
  public int id() { return id; }

  public record Point(int row, int column) implements Comparable<Point> {
    @Override
    public int compareTo(Point o) {
      int cmp = Integer.compare(this.row, o.row);
      return cmp != 0 ? cmp : Integer.compare(this.column, o.column);
    }
  }
}
