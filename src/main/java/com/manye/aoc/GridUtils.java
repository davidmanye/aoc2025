package com.manye.aoc;

public class GridUtils {

  public static void printGridPretty(Object[][] grid) {
    if (grid == null) return;

    // 1. Calculate max width for every column
    int cols = 0;
    for (Object[] row : grid) if (row != null) cols = Math.max(cols, row.length);

    int[] colWidths = new int[cols];
    for (Object[] row : grid) {
      if (row == null) continue;
      for (int c = 0; c < row.length; c++) {
        String s = String.valueOf(row[c]);
        colWidths[c] = Math.max(colWidths[c], s.length());
      }
    }

    // 2. Print with padding
    for (Object[] row : grid) {
      if (row == null) {
        System.out.println("---");
        continue;
      }
      for (int c = 0; c < row.length; c++) {
        String val = (c < row.length) ? String.valueOf(row[c]) : "";
        // %-5s means "left justify, width 5"
        System.out.printf("%-" + (colWidths[c] + 2) + "s", val);
      }
      System.out.println();
    }
  }

  public static void printGridPretty(long[][] grid) {
    if (grid == null) return;

    // 1. Calculate max width for every column
    int cols = 0;
    for (long[] row : grid) if (row != null) cols = Math.max(cols, row.length);

    int[] colWidths = new int[cols];
    for (long[] row : grid) {
      if (row == null) continue;
      for (int c = 0; c < row.length; c++) {
        String s = String.valueOf(row[c]);
        colWidths[c] = Math.max(colWidths[c], s.length());
      }
    }

    // 2. Print with padding
    for (long[] row : grid) {
      if (row == null) {
        System.out.println("---");
        continue;
      }
      for (int c = 0; c < row.length; c++) {
        String val = (c < row.length) ? String.valueOf(row[c]) : "";
        // %-5s means "left justify, width 5"
        System.out.printf("%-" + (colWidths[c] + 2) + "s", val);
      }
      System.out.println();
    }
  }

  public static void printGridPretty(char[][] grid) {
    if (grid == null) return;

    // 1. Calculate max width for every column
    int cols = 0;
    for (char[] row : grid) if (row != null) cols = Math.max(cols, row.length);

    int[] colWidths = new int[cols];
    for (char[] row : grid) {
      if (row == null) continue;
      for (int c = 0; c < row.length; c++) {
        String s = String.valueOf(row[c]);
        colWidths[c] = Math.max(colWidths[c], s.length());
      }
    }

    // 2. Print with padding
    for (char[] row : grid) {
      if (row == null) {
        System.out.println("---");
        continue;
      }
      for (int c = 0; c < row.length; c++) {
        String val = (c < row.length) ? String.valueOf(row[c]) : "";
        // %-5s means "left justify, width 5"
        System.out.printf("%-" + (colWidths[c] + 2) + "s", val);
      }
      System.out.println();
    }
  }
}
