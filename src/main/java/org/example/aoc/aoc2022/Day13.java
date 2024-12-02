package org.example.aoc.aoc2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day13 extends AoC2022Day<String> {

    private record Packet(int number, List<Packet> innerPackets) {
    }

    protected record Pair(Packet leftPacket, Packet rightPacket) {
    }

    @Override
    protected String parseInput(String strInput) {

        return strInput;
    }

    @Override
    protected Integer partOne(String input) {

        final List<Pair> pairs = Arrays.stream(input.split(System.lineSeparator() + System.lineSeparator()))
                .map(pairString -> {
                    final String[] split = pairString.split(System.lineSeparator());

                    return new Pair(buildPacket(split[0]), buildPacket(split[1]));
                })
                .toList();

        return IntStream.range(1, pairs.size() + 1)
                .filter(i -> comparePackets(pairs.get(i - 1).leftPacket, pairs.get(i - 1).rightPacket) == -1)
                .sum();
    }

    private Packet buildPacket(String pairString) {

        final ArrayList<Packet> packets = new ArrayList<>();
        buildPackets(pairString.split(""), 0, packets);

        return new Packet(0, packets);
    }

    private int buildPackets(String[] chars, int index, ArrayList<Packet> packets) {

        StringBuilder sb = new StringBuilder();

        int i = index;

        while (i < chars.length) {

            if (Character.isDigit(chars[i].charAt(0))) {

                sb.append(chars[i]);

                i++;
            } else {

                if (!sb.isEmpty()) {

                    packets.add(new Packet(Integer.parseInt(sb.toString()), null));
                    sb = new StringBuilder();
                }

                switch (chars[i]) {
                    case "," -> i++;
                    case "]" -> {
                        return i + 1;
                    }
                    case "[" -> {
                        final ArrayList<Packet> innerPackets = new ArrayList<>();
                        i = buildPackets(chars, i + 1, innerPackets);
                        packets.add(new Packet(0, innerPackets));
                    }
                    default -> {
                        // Do nothing
                    }
                }
            }
        }

        if (!sb.isEmpty()) {

            packets.add(new Packet(Integer.parseInt(sb.toString()), null));
        }

        return index;
    }

    private int comparePackets(Packet packet1, Packet packet2) {

        if (packet1.innerPackets == null && packet2.innerPackets == null) {

            return Integer.compare(packet1.number, packet2.number);
        }

        final List<Packet> packets1 = packet1.innerPackets == null ?
                List.of(new Packet(packet1.number, null)) :
                packet1.innerPackets;

        final List<Packet> packets2 = packet2.innerPackets == null ?
                List.of(new Packet(packet2.number, null)) :
                packet2.innerPackets;

        int i = 0;

        for (; i < packets1.size() && i < packets2.size(); i++) {

            final int comparison = comparePackets(packets1.get(i), packets2.get(i));

            if (comparison != 0) {

                return comparison;
            }
        }

        if (i == packets1.size() && i == packets2.size()) {

            return 0;
        }

        return i == packets1.size() && i < packets2.size() ? -1 : 1;
    }

    @Override
    protected Integer partTwo(String input) {

        final List<Packet> packets = Arrays.stream(input.split(System.lineSeparator() + System.lineSeparator()))
                .flatMap(pairString -> Arrays.stream(pairString.split(System.lineSeparator())))
                .map(this::buildPacket)
                .collect(Collectors.toList());

        final Packet dividerPacket1 = buildPacket("[[2]]");
        final Packet dividerPacket2 = buildPacket("[[6]]");

        packets.add(dividerPacket1);
        packets.add(dividerPacket2);

        packets.sort(this::comparePackets);

        return IntStream.range(1, packets.size() + 1)
                .filter(i -> packets.get(i - 1) == dividerPacket1 || packets.get(i - 1) == dividerPacket2)
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    protected String getDay() {

        return "day13";
    }
}
