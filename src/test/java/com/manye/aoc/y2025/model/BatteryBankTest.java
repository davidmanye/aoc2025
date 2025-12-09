package com.manye.aoc.y2025.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BatteryBankTest {

  private static Stream<Arguments> maxJoltageOf2TestSet() {
    return Stream.of(
        Arguments.of("987654321111111", 98L),
        Arguments.of("811111111111119", 89L),
        Arguments.of("234234234234278", 78L),
        Arguments.of("818181911112111", 92L)
    );
  }

  private static Stream<Arguments> maxJoltageOf12TestSet() {
    return Stream.of(
        Arguments.of("987654321111111", 987654321111L),
        Arguments.of("811111111111119", 811111111119L),
        Arguments.of("234234234234278", 434234234278L),
        Arguments.of("818181911112111", 888911112111L)
    );
  }

  @ParameterizedTest(name = "[{index}] BatteryBank: {0} -> Max joltage: {1}")
  @MethodSource("maxJoltageOf2TestSet")
  void maxJoltageOf2(String input, long maxJoltage) {
    var bank = new BatteryBank(input);

    assertThat(bank.getMaxJoltageOfDigitsLength(2)).isEqualTo(maxJoltage);
  }

  @ParameterizedTest(name = "[{index}] BatteryBank: {0} -> Max joltage: {1}")
  @MethodSource("maxJoltageOf12TestSet")
  void maxJoltageOf12(String input, long maxJoltage) {
    var bank = new BatteryBank(input);

    assertThat(bank.getMaxJoltageOfDigitsLength(12)).isEqualTo(maxJoltage);
  }
}
