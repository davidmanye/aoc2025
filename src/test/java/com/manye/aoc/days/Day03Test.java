package com.manye.aoc.days;

import static org.assertj.core.api.Assertions.assertThat;

import com.manye.aoc.Day;
import com.manye.aoc.InputReader;
import org.junit.jupiter.api.Test;

class Day03Test {

  public static final String INPUT = "/test/inputs/day03.txt";
  private final Day day = new Day03();

  @Test
  void part1() {
    var expected = "357";

    var actual = day.part1(InputReader.readInput(INPUT));

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void part2() {
    var expected = "";

    var actual = day.part2(InputReader.readInput(INPUT));

    assertThat(actual).isEqualTo(expected);
  }
}
