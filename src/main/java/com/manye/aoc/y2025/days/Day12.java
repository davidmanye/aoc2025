package com.manye.aoc.y2025.days;

import com.manye.aoc.Day;
import com.manye.aoc.y2025.model.PackingSolver;

public class Day12 implements Day {

  @Override
  public String part1(String input) {
    var solver = new PackingSolver(input);
    return String.valueOf(solver.countSolvableRegions());
  }

  @Override
  public String part2(String input) {
    return "Not yet implemented";
  }
}
