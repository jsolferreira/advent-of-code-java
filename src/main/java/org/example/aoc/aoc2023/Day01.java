package org.example.aoc.aoc2023;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day01 extends AoC2023Day<List<String>> {

    @Override
    protected List<String> parseInput(String strInput) {

        return strInput.lines().toList();
    }

    @Override
    protected Integer partOne(List<String> input) {

        return input.stream()
                .reduce(0, (acc, val) -> acc + findFirstDigit(val) * 10 + findLastDigit(val), Integer::sum);
    }

    private int findFirstDigit(String s) {

        return findDigit(s, s::charAt);
    }

    private int findLastDigit(String s) {

        return findDigit(s, i -> s.charAt(s.length() - i - 1));
    }

    private int findDigit(String s, IntFunction<Character> stringCharGetter) {

        return IntStream.range(0, s.length())
                .mapToObj(stringCharGetter)
                .filter(Character::isDigit)
                .findFirst()
                .map(Character::getNumericValue)
                .orElse(0);
    }

    @Override
    protected Integer partTwo(List<String> input) {

        final Map<String, Character> digits = Map.of("one", '1',
                                                     "two", '2',
                                                     "three", '3',
                                                     "four", '4',
                                                     "five", '5',
                                                     "six", '6',
                                                     "seven", '7',
                                                     "eight", '8',
                                                     "nine", '9');

        int sum = 0;

        for (String s : input) {

            Character firstDigit = null;

            for (int i = 0; i < s.length(); i++) {

                char c = s.charAt(i);

                if (Character.isDigit(c)) {
                    firstDigit = c;
                    break;
                }

                int offset = 0;
                Set<String> possibleDigits = digits.keySet();

                while (possibleDigits.size() > 1) {

                    c = s.charAt(i + offset);

                    int finalOffset = offset;
                    char finalC = c;
                    possibleDigits = possibleDigits.stream().filter(z -> z.charAt(finalOffset) == finalC).collect(Collectors.toSet());
                    offset++;
                }

                if (possibleDigits.size() == 1) {

                    String digit = possibleDigits.iterator().next();

                    if (digit.length() == offset) {

                        firstDigit = digits.get(possibleDigits.iterator().next());
                        break;
                    } else {

                        boolean matches = true;

                        while (digit.length() != offset && (i + offset < s.length()) && matches) {

                            matches = s.charAt(i + offset) == digit.charAt(offset);
                            offset++;
                        }

                        if (matches) {

                            firstDigit = digits.get(possibleDigits.iterator().next());
                            break;
                        }
                    }
                }
            }

            Character lastDigit = null;

            for (int j = s.length() - 1; j >= 0; j--) {

                char c = s.charAt(j);

                if (Character.isDigit(c)) {
                    lastDigit = c;
                    break;
                }

                int offset = 0;
                Set<String> possibleDigits = digits.keySet();

                while (possibleDigits.size() > 1) {

                    c = s.charAt(j - offset);

                    int finalOffset = offset;
                    char finalC = c;
                    possibleDigits = possibleDigits
                            .stream()
                            .filter(z -> z.charAt(z.length() - 1 - finalOffset) == finalC)
                            .collect(Collectors.toSet());
                    offset++;
                }

                if (possibleDigits.size() == 1) {

                    String digit = possibleDigits.iterator().next();

                    if (digit.length() == offset) {

                        lastDigit = digits.get(possibleDigits.iterator().next());
                        break;
                    } else {

                        boolean matches = true;

                        while (digit.length() != offset && offset <= j && matches) {

                            matches = s.charAt(j - offset) == digit.charAt(digit.length() - 1 - offset);
                            offset++;
                        }

                        if (matches) {

                            lastDigit = digits.get(possibleDigits.iterator().next());
                            break;
                        }
                    }
                }
            }

            sum += Character.getNumericValue(firstDigit) * 10 + Character.getNumericValue(lastDigit);
        }

        return sum;
    }

    @Override
    protected String getDay() {

        return "day01";
    }
}
