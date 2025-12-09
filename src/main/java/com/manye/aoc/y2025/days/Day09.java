package com.manye.aoc.y2025.days;

import com.manye.aoc.Day;
import com.manye.aoc.y2025.model.MovieTheater;

public class Day09 implements Day {

  @Override
  public String part1(String input) {
    var theater = new MovieTheater(input);
    return String.valueOf(theater.findLargestRectangleArea());
  }

  @Override
  public String part2(String input) {
    var theater = new MovieTheater(input);
    return String.valueOf(theater.findLargestRestrictedRectangle());
  }
}
