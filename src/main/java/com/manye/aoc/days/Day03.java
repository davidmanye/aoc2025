package com.manye.aoc.days;

import com.manye.aoc.Day;
import com.manye.aoc.model.BatteryBank;

public class Day03 implements Day {

  @Override
  public String part1(String input) {
    var result = input.lines().map(BatteryBank::new).mapToLong(bank -> bank.getMaxJoltageOfDigitsLength(2)).sum();
    return String.valueOf(result);
  }

  @Override
  public String part2(String input) {
    var result = input.lines().map(BatteryBank::new).mapToLong(bank -> bank.getMaxJoltageOfDigitsLength(12)).sum();
    return String.valueOf(result);
  }

}
