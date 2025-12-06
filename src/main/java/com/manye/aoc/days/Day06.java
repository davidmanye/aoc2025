package com.manye.aoc.days;

import com.manye.aoc.Day;
import com.manye.aoc.model.MathWorksheet;
import com.manye.aoc.model.Range;
import java.util.ArrayList;
import java.util.List;

public class Day06 implements Day {

  @Override
  public String part1(String input) {
    final var mathWorksheet = MathWorksheet.fromHorizontalRules(input);
    return mathWorksheet.sumOfAllSolutions() + "";
  }

  @Override
  public String part2(String input) {
    final var mathWorksheet = MathWorksheet.fromVerticalRules(input);
    return mathWorksheet.sumOfAllSolutions() + "";
  }
}
