package org.example.aoc.aoc2023;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

class Day07 extends AoC2023Day<List<Day07.Hand>> {

    private boolean jokerMode = false;

    private static final Map<Character, Integer> cardSymbolValues = Map.ofEntries(Map.entry('A', 13),
                                                                                  Map.entry('K', 12),
                                                                                  Map.entry('Q', 11),
                                                                                  Map.entry('J', 10),
                                                                                  Map.entry('T', 9),
                                                                                  Map.entry('9', 8),
                                                                                  Map.entry('8', 7),
                                                                                  Map.entry('7', 6),
                                                                                  Map.entry('6', 5),
                                                                                  Map.entry('5', 4),
                                                                                  Map.entry('4', 3),
                                                                                  Map.entry('3', 2),
                                                                                  Map.entry('2', 1));

    private enum HandType {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int value;

        HandType(int value) {

            this.value = value;
        }
    }

    private record Strength(Hand hand, HandType handType) {
    }

    protected record Hand(String cards, int bid) {}

    @Override
    protected List<Hand> parseInput(String strInput) {

        return strInput.lines()
                .map(s -> {
                    final String[] split = s.split("\\s+");

                    return new Hand(split[0], Integer.parseInt(split[1]));
                })
                .toList();
    }

    @Override
    protected Integer partOne(List<Hand> input) {

        jokerMode = false;

        final TreeSet<Strength> strengths = input.stream()
                .map(hand -> {
                    final Map<Character, Long> cardsCounted = countCards(hand.cards);
                    final List<Long> twoMostFrequentCards = getTopTwoMostFrequentCards(cardsCounted);

                    long firstCount = twoMostFrequentCards.get(0);
                    long secondCount = 0;

                    if (twoMostFrequentCards.size() == 2) {
                        secondCount = twoMostFrequentCards.get(1);
                    }

                    final HandType handType = getHandType(firstCount, secondCount);

                    return new Strength(hand, handType);
                })
                .collect(Collectors.toCollection(() -> new TreeSet<>(this::compareStrengths)));

        int sum = 0;
        int i = strengths.size();

        for (Strength strength : strengths) {
            sum += i * strength.hand.bid;
            i--;
        }

        return sum;
    }

    @Override
    protected Integer partTwo(List<Hand> input) {

        jokerMode = true;

        final TreeSet<Strength> strengths = input.stream()
                .map(hand -> {
                    final Map<Character, Long> cardsCounted = countCards(hand.cards);
                    final List<Long> twoMostFrequentCards = getTopTwoMostFrequentCards(cardsCounted);

                    long firstCount = 0;
                    long secondCount = 0;

                    if (!twoMostFrequentCards.isEmpty()) {
                        firstCount = twoMostFrequentCards.getFirst();
                    }

                    if (twoMostFrequentCards.size() == 2) {
                        secondCount = twoMostFrequentCards.get(1);
                    }

                    final long jokerCount = cardsCounted
                            .entrySet()
                            .stream()
                            .filter(x -> x.getKey() == 'J')
                            .map(Map.Entry::getValue)
                            .findFirst()
                            .orElse(0L);

                    final HandType handType = getHandType(firstCount, secondCount, jokerCount);

                    return new Strength(hand, handType);
                })
                .collect(Collectors.toCollection(() -> new TreeSet<>(this::compareStrengths)));

        int sum = 0;
        int i = strengths.size();

        for (Strength strength : strengths) {
            sum += i * strength.hand.bid;
            i--;
        }

        return sum;
    }

    private int compareStrengths(Strength a, Strength b) {

        if (a.handType != b.handType) {
            return a.handType.value > b.handType.value ? -1 : 1;
        }

        for (int i = 0; i < a.hand.cards.length(); i++) {

            final char c1 = a.hand.cards.charAt(i);
            final char c2 = b.hand.cards.charAt(i);

            final int equal = cardSymbolValues.get(c2).compareTo(cardSymbolValues.get(c1));
            if (equal != 0) {
                if (jokerMode) {
                    if (c1 == 'J') {
                        return 1;
                    }
                    if (c2 == 'J') {
                        return -1;
                    }
                }

                return equal;
            }
        }

        return 0;
    }

    private Map<Character, Long> countCards(String cards) {

        return cards
                .chars()
                .mapToObj(x -> (char) x)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<Long> getTopTwoMostFrequentCards(Map<Character, Long> cardsCounted) {

        return cardsCounted
                .entrySet()
                .stream()
                .filter(x -> !jokerMode || x.getKey() != 'J')
                .sorted(Comparator.comparingLong(x -> -x.getValue()))
                .limit(2)
                .map(Map.Entry::getValue)
                .toList();
    }

    private HandType getHandType(long nCards1, long nCards2) {

        return getHandType(nCards1, nCards2, 0);
    }

    private HandType getHandType(long nCards1, long nCards2, long jokerCount) {

        if (nCards1 + jokerCount == 5) {
            return HandType.FIVE_OF_A_KIND;
        }

        if (nCards1 + jokerCount == 4) {
            return HandType.FOUR_OF_A_KIND;
        }

        if (nCards1 + nCards2 + jokerCount == 5) {
            return HandType.FULL_HOUSE;
        }

        if (nCards1 + jokerCount == 3) {
            return HandType.THREE_OF_A_KIND;
        }

        if (nCards1 + nCards2 == 4) {
            return HandType.TWO_PAIR;
        }

        if (nCards1 + jokerCount == 2) {
            return HandType.ONE_PAIR;
        }

        return HandType.HIGH_CARD;
    }

    @Override
    protected String getDay() {

        return "day07";
    }
}
