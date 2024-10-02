package org.example.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day16 extends AoC2020Day<Day16.Note> {

    private record Ticket(List<Long> values) {
    }

    private record Range(long start, long end) {

        public boolean isInRange(long value) {

            return start <= value && value <= end;
        }
    }

    protected record Note(Map<String, List<Range>> fields, Ticket ticket, List<Ticket> nearbyTickets) {
    }

    @Override
    protected Note parseInput(String strInput) {

        final String[] firstSplit = strInput.split("\\s\\syour ticket:\\s");
        final String fieldsBlock = firstSplit[0];
        final String[] secondSplit = firstSplit[1].split("\\s\\snearby tickets:\\s");
        final String ticketsBlock = secondSplit[0].trim();
        final String nearbyTicketsBlock = secondSplit[1].trim();

        final Map<String, List<Range>> ranges = parseRanges(fieldsBlock);
        final Ticket ticket = parseTicket(ticketsBlock);
        final List<Ticket> nearbyTickets = parseNearbyTickets(nearbyTicketsBlock);

        return new Note(ranges, ticket, nearbyTickets);
    }

    private Map<String, List<Range>> parseRanges(String fieldsBlock) {

        final Pattern pattern = Pattern.compile("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)");

        final Matcher matcher = pattern.matcher(fieldsBlock);

        return IntStream.iterate(0, i -> matcher.find(), i -> i + 1)
                .boxed()
                .collect(Collectors.toMap(i -> matcher.group(1), i -> {
                    final Range firstRange = new Range(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                    final Range secondRange = new Range(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));

                    return List.of(firstRange, secondRange);
                }));
    }

    private Ticket parseTicket(String ticketsBlock) {

        final List<Long> values = Arrays.stream(ticketsBlock.split(","))
                .map(Long::parseLong)
                .toList();

        return new Ticket(values);
    }

    private List<Ticket> parseNearbyTickets(String nearbyTicketsBlock) {

        return nearbyTicketsBlock.lines()
                .map(this::parseTicket)
                .toList();
    }

    @Override
    protected Long partOne(Note input) {

        return input.nearbyTickets.stream()
                .flatMap(nearbyTicket -> nearbyTicket.values
                        .stream()
                        .filter(value -> input.fields
                                .values()
                                .stream()
                                .noneMatch(ranges -> isValueInAnyRange(ranges, value))))
                .reduce(0L, Long::sum);
    }

    @Override
    protected Long partTwo(Note input) {

        final List<Ticket> validTickets = getValidTickets(input);

        final List<List<String>> fields = transformIntoListOfPossibleFields(validTickets, input);

        while (isNotSolved(fields)) {

            fields.stream()
                    .filter(field -> field.size() == 1)
                    .forEach(field -> {

                        for (int j = 0; j < fields.size(); j++) {

                            if (fields.get(j).size() > 1) {

                                fields.set(j, fields.get(j).stream().filter(p -> !p.equals(field.getFirst())).toList());
                            }
                        }
                    });
        }

        return IntStream.range(0, fields.size())
                .filter(i -> fields.get(i).getFirst().startsWith("departure"))
                .mapToLong(input.ticket.values::get)
                .reduce(1L,
                        (acc, val) -> acc * val);
    }

    private boolean isValueInAnyRange(List<Range> ranges, long value) {

        return ranges.stream().anyMatch(range -> range.isInRange(value));
    }

    private List<Ticket> getValidTickets(Note input) {

        return input.nearbyTickets.stream()
                .filter(nearbyTicket -> nearbyTicket.values.stream()
                        .allMatch(value -> input.fields
                                .values()
                                .stream()
                                .anyMatch(ranges -> isValueInAnyRange(ranges, value))))
                .toList();
    }

    private List<List<String>> transformIntoListOfPossibleFields(List<Ticket> tickets, Note input) {

        final int numberOfFieldsInEachTicket = tickets.getFirst().values.size();

        return IntStream.range(0, numberOfFieldsInEachTicket)
                .<List<String>>mapToObj(j -> tickets.stream()
                        .map(integer -> integer.values.get(j))
                        .<List<String>>reduce(new ArrayList<>(),
                                              (acc, val) -> {

                                                  final List<String> strings = input.fields.entrySet().stream()
                                                          .filter(entry -> isValueInAnyRange(entry.getValue(), val))
                                                          .map(Map.Entry::getKey)
                                                          .toList();

                                                  if (acc.isEmpty()) {

                                                      return strings;
                                                  } else {

                                                      return acc.stream()
                                                              .filter(strings::contains)
                                                              .toList();
                                                  }
                                              },
                                              (a, b) -> new ArrayList<>())
                )
                .toList();
    }

    private boolean isNotSolved(List<List<String>> grid) {

        return grid.stream()
                .anyMatch(row -> row.size() > 1);
    }

    @Override
    protected String getDay() {

        return "day16";
    }
}
