package org.example.aoc.aoc2020;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class Day13 extends AoC2020Day<Day13.Note> {

    private record Bus(int id, int position) {
    }

    protected record Note(long timestamp, List<Bus> buses) {
    }

    @Override
    protected Note parseInput(String strInput) {

        final String[] split = strInput.split(System.lineSeparator());

        final long timestamp = Long.parseLong(split[0]);
        final String[] busIds = split[1].split(",");

        final List<Bus> buses = IntStream.range(0, busIds.length)
                .filter(i -> !busIds[i].equals("x"))
                .mapToObj(i -> new Bus(Integer.parseInt(busIds[i]), i))
                .toList();

        return new Note(timestamp, buses);
    }

    @Override
    protected Long partOne(Note input) {

        long timestamp = input.timestamp;

        while (true) {

            long finalTimestamp = timestamp;
            final Optional<Integer> busId = input.buses.stream()
                    .filter(bus -> finalTimestamp % bus.id == 0)
                    .findFirst()
                    .map(bus -> bus.id);

            if (busId.isPresent()) {

                return (timestamp - input.timestamp) * busId.get();
            }

            timestamp++;
        }
    }

    @Override
    protected Long partTwo(Note input) {

        return null;
    }

    @Override
    protected String getDay() {

        return "day13";
    }
}
