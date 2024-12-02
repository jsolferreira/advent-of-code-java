package org.example.aoc.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day04 extends AoC2023Day<List<Day04.Card>> {

    protected record Card(int id, List<Integer> winningNumbers, List<Integer> myNumbers) {}

    @Override
    protected List<Card> parseInput(String strInput) {

        return strInput.lines()
                .map(s -> {
                    final String[] split = s.split(":");
                    final int id = Integer.parseInt(split[0].split("\\s+")[1]);
                    final String[] split2 = split[1].split("\\|");
                    final List<Integer> winningNumbers = Arrays.stream(split2[0].trim().split("\\s+")).map(Integer::parseInt).toList();
                    final List<Integer> myNumbers = Arrays.stream(split2[1].trim().split("\\s+")).map(Integer::parseInt).toList();

                    return new Card(id, winningNumbers, myNumbers);
                })
                .toList();
    }

    @Override
    protected Double partOne(List<Card> input) {

        return input.stream()
                .map(card -> card.myNumbers.stream().filter(card.winningNumbers::contains).count())
                .filter(count -> count > 0)
                .reduce(0D, (acc, val) -> acc + Math.pow(2, val - 1D), Double::sum);
    }

    @Override
    protected Integer partTwo(List<Card> input) {

        final Map<Integer, Integer> nCards = new HashMap<>();
        int sum = 0;

        for (Card card : input) {
            long count = card.myNumbers.stream().filter(card.winningNumbers::contains).count();

            Integer orDefault = nCards.getOrDefault(card.id, 1);

            for (int i = 0; i < orDefault; i++) {

                for (int j = 1; j <= count; j++) {

                    final int id = card.id + j;

                    nCards.putIfAbsent(id, 1);
                    nCards.merge(id, 1, Integer::sum);
                }
            }

            sum += orDefault;
        }

        return sum;
    }

    @Override
    protected String getDay() {

        return "day04";
    }
}
