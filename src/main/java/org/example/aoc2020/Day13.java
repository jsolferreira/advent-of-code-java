package org.example.aoc2020;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class Day13 extends AbstractAoC2020<Long, Day13.Note> {

    private record Bus(int id, int position) {
    }

    protected record Note(long timestamp, List<Bus> buses) {
    }

    @Override
    protected Note parseInput(String strInput) {

        final String[] split = strInput.split("\n");

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

        long timestamp = input.timestamp;
        final int firstBusId = input.buses.get(0).id;

        while (true) {

            long finalTimestamp = timestamp;
            boolean z = input.buses.stream()
                    .allMatch(bus -> (finalTimestamp + bus.position) % bus.id == 0);

            if (z) {

                return timestamp;
            }

            timestamp += firstBusId - (timestamp % firstBusId);
        }
    }

    @Override
    protected String getDay() {

        return "day13";
    }
}
