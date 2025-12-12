package com.manye.aoc.y2025.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PackingSolver {

  private final Map<Integer, List<Shape>> shapeVariations = new HashMap<>();
  private final List<RegionTask> tasks = new ArrayList<>();

  public PackingSolver(String input) {
    parseInput(input);
  }

  public long countSolvableRegions() {
    return tasks.stream()
        .filter(this::canFitPresents)
        .count();
  }

  // --- The Backtracking Algorithm ---

  private boolean canFitPresents(RegionTask task) {
    // 1. Optimization: Check Area
    int totalArea = task.presents.stream().mapToInt(Shape::area).sum();
    if (totalArea > task.width * task.height) return false;

    // 2. Sort presents (Largest first = Fail Fast)
    List<Shape> toPlace = new ArrayList<>(task.presents);
    toPlace.sort(Comparator.comparingInt(Shape::area).reversed());

    boolean[][] grid = new boolean[task.height][task.width];
    return solveRecursive(grid, toPlace, 0);
  }

  private boolean solveRecursive(boolean[][] grid, List<Shape> pieces, int index) {
    // Base Case: All pieces placed successfully
    if (index == pieces.size()) {
      return true;
    }

    Shape currentPieceBase = pieces.get(index);
    List<Shape> variations = shapeVariations.get(currentPieceBase.id());

    // Iterate over every cell in the grid
    int rows = grid.length;
    int cols = grid[0].length;

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < cols; column++) {

        // Try every rotation/flip of the current piece
        for (Shape variant : variations) {
          if (canPlace(grid, variant, row, column)) {

            // DO: Place
            place(grid, variant, row, column, true);

            // RECURSE
            if (solveRecursive(grid, pieces, index + 1)) {
              return true;
            }

            // UNDO: Backtrack
            place(grid, variant, row, column, false);
          }
        }
      }
    }

    return false;
  }

  private boolean canPlace(boolean[][] grid, Shape s, int indexRow, int indexColumn) {
    for (Shape.Point p : s.points()) {
      int nextRow = indexRow + p.row();
      int nextColumn = indexColumn + p.column();

      // Bounds Check
      if (nextRow < 0 || nextRow >= grid.length || nextColumn < 0 || nextColumn >= grid[0].length) {
        return false;
      }
      // Collision Check
      if (grid[nextRow][nextColumn]) {
        return false;
      }
    }
    return true;
  }

  private void place(boolean[][] grid, Shape s, int r, int c, boolean val) {
    for (Shape.Point p : s.points()) {
      grid[r + p.row()][c + p.column()] = val;
    }
  }

  // --- Parsing Logic ---

  private void parseInput(String input) {
    String[] sections = input.split("\n\n(?=\\d+x\\d+)"); // Split shapes from regions

    // 1. Parse Shapes
    String[] shapeBlocks = sections[0].split("\n\n");
    for (String block : shapeBlocks) {
      Scanner sc = new Scanner(block);
      String header = sc.nextLine(); // "0:"
      int id = Integer.parseInt(header.replace(":", "").trim());
      Set<Shape.Point> points = new HashSet<>();
      int row = 0;
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        for (int column = 0; column < line.length(); column++) {
          if (line.charAt(column) == '#') points.add(new Shape.Point(row, column));
        }
        row++;
      }
      Shape base = new Shape(id, points);
      shapeVariations.put(id, base.generateVariations());
    }

    // 2. Parse Region Tasks
    if (sections.length > 1) {
      // We use split("\n") but filter out empty lines to avoid parsing errors
      Arrays.stream(sections[1].split("\n"))
          .filter(line -> !line.isBlank())
          .forEach(line -> {
            // "12x5: 1 0 1..."
            String[] parts = line.split(": ");
            String[] dims = parts[0].split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            List<Shape> presents = new ArrayList<>();
            String[] counts = parts[1].trim().split("\\s+");
            for (int id = 0; id < counts.length; id++) {
              int count = Integer.parseInt(counts[id]);
              // Add 'count' copies of the base shape
              // We grab the FIRST variation (base) just as a reference ID holder
              // The solver will look up all variations later using the ID.
              Shape base = shapeVariations.get(id).get(0);
              for (int k = 0; k < count; k++) presents.add(base);
            }
            tasks.add(new RegionTask(width, height, presents));
          });
    }
  }

  private record RegionTask(int width, int height, List<Shape> presents) {}
}
