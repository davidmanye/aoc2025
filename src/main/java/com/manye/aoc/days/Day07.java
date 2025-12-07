package com.manye.aoc.days;

import com.manye.aoc.Day;
import com.manye.aoc.model.TachyonManifold;

public class Day07 implements Day {

  @Override
  public String part1(String input) {
    var manifold = new TachyonManifold(input);
    final var result = manifold.countSplits();
    manifold.printGrid();
    return String.valueOf(result);
  }

  @Override
  public String part2(String input) {
    var manifold = new TachyonManifold(input);
    final var result = manifold.countTimelines();
    manifold.printMemo();
    return String.valueOf(result);
  }
}
