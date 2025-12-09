package com.manye.aoc.y2025.model;

import java.util.List;

public class MovieTheater {

  private final List<Point2D> polygon;

  public MovieTheater(String input) {
    this.polygon = input.lines()
        .map(MovieTheater::parse)
        .toList();
  }

  public long findLargestRectangleArea() {
    long maxArea = 0;

    // Iterate all unique pairs
    for (int i = 0; i < polygon.size(); i++) {
      for (int j = i + 1; j < polygon.size(); j++) {

        Point2D t1 = polygon.get(i);
        Point2D t2 = polygon.get(j);

        long width = Math.abs(t1.x() - t2.x()) + 1;
        long height = Math.abs(t1.y() - t2.y()) + 1;
        long area = width * height;

        if (area > maxArea) {
          maxArea = area;
        }
      }
    }
    return maxArea;
  }

  public long findLargestRestrictedRectangle() {
    long maxArea = 0;
    int n = polygon.size();

    // Iterate all pairs of Red Tiles to form a candidate rectangle
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        Point2D p1 = polygon.get(i);
        Point2D p2 = polygon.get(j);

        if (isValidRectangle(p1, p2)) {
          long width = Math.abs(p1.x() - p2.x()) + 1;
          long height = Math.abs(p1.y() - p2.y()) + 1;
          maxArea = Math.max(maxArea, width * height);
        }
      }
    }
    return maxArea;
  }

  /**
   * Checks if the rectangle defined by corners p1 and p2 is fully contained
   * within the red/green loop (polygon).
   */
  private boolean isValidRectangle(Point2D p1, Point2D p2) {
    long minX = Math.min(p1.x(), p2.x());
    long maxX = Math.max(p1.x(), p2.x());
    long minY = Math.min(p1.y(), p2.y());
    long maxY = Math.max(p1.y(), p2.y());

    // 1. CHECK: Does the polygon invade the rectangle?
    // Iterate all polygon edges and vertices
    int n = polygon.size();
    for (int k = 0; k < n; k++) {
      Point2D v = polygon.get(k);
      Point2D nextV = polygon.get((k + 1) % n); // Wrap around

      // A. Vertex strictly inside check
      if (v.x() > minX && v.x() < maxX && v.y() > minY && v.y() < maxY) {
        return false; // Found a red tile inside the rectangle -> Invalid
      }

      // B. Edge crossing check (Splitting the rectangle)
      // Vertical Edge crossing?
      if (v.x() == nextV.x()) {
        long edgeX = v.x();
        long startY = Math.min(v.y(), nextV.y());
        long endY = Math.max(v.y(), nextV.y());

        // If edge X is strictly between rectangle X's...
        if (edgeX > minX && edgeX < maxX) {
          // ...and the edge completely spans the rectangle's height
          if (startY <= minY && endY >= maxY) return false;
        }
      }

      // Horizontal Edge crossing?
      if (v.y() == nextV.y()) {
        long edgeY = v.y();
        long startX = Math.min(v.x(), nextV.x());
        long endX = Math.max(v.x(), nextV.x());

        // If edge Y is strictly between rectangle Y's...
        if (edgeY > minY && edgeY < maxY) {
          // ...and the edge completely spans the rectangle's width
          if (startX <= minX && endX >= maxX) return false;
        }
      }
    }

    // 2. CHECK: Is the rectangle inside the polygon?
    // If no edges crossed it, we just need to test one point (the center).
    double centerX = (minX + maxX) / 2.0;
    double centerY = (minY + maxY) / 2.0;

    return isPointInPolygon(centerX, centerY);
  }

  /**
   * Ray Casting Algorithm (Jordan Curve Theorem)
   * Counts how many times a ray from (x,y) to the right crosses polygon edges.
   */
  private boolean isPointInPolygon(double x, double y) {
    boolean inside = false;
    int n = polygon.size();

    for (int i = 0; i < n; i++) {
      Point2D v1 = polygon.get(i);
      Point2D v2 = polygon.get((i + 1) % n);

      // Check if the point is exactly on the edge (Boundary is valid/green)
      if (isOnSegment(x, y, v1, v2)) return true;

      // Check for ray intersection
      // We look for segments that straddle the 'y' line
      boolean yStraddle = (v1.y() > y) != (v2.y() > y);

      if (yStraddle) {
        // Calculate X coordinate where the edge intersects the ray line y
        double intersectX = (v2.x() - v1.x()) * (y - v1.y()) / (v2.y() - v1.y()) + v1.x();
        if (x < intersectX) {
          inside = !inside;
        }
      }
    }
    return inside;
  }

  private boolean isOnSegment(double px, double py, Point2D v1, Point2D v2) {
    // Check collinearity and bounds
    // Since it's rectilinear, simple bounds check is enough if coordinates match
    if (v1.x() == v2.x()) { // Vertical edge
      return Math.abs(px - v1.x()) < 0.0001 && py >= Math.min(v1.y(), v2.y()) && py <= Math.max(v1.y(), v2.y());
    } else { // Horizontal edge
      return Math.abs(py - v1.y()) < 0.0001 && px >= Math.min(v1.x(), v2.x()) && px <= Math.max(v1.x(), v2.x());
    }
  }

  public static Point2D parse(String input) {
    var numbers = input.split(",");
    return new Point2D(Long.parseLong(numbers[0]), Long.parseLong(numbers[1]));
  }
}
